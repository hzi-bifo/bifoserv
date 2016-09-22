package de.unibi.cebitec.bibiserv.sequence.parser;

import de.unibi.cebitec.bibiserv.sequenceparser.tools.PatternType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The basic functionality of all xml parsers.
 *
 * @author gatter
 */
public abstract class AbstractXMLParser implements XMLParser {

    protected BufferedWriter output;
    protected BufferedReader input;
    protected String sequenceTypeName;
    protected PatternType patternType;
    protected List<String> warnings;
    private static final int MAX_BUFFER_SIZE = 3 * 1024;
    protected String XSD_LOCATION = "/de/unibi/cebitec/bibiserv/sequence/validator/xsd/";
    /**
     * How much was already read?
     */
    protected int readLength;
    /**
     * Maximum number of chars to read, -1 for endless
     */
    protected int maxLength = -1;

    public AbstractXMLParser(BufferedReader input, BufferedWriter output,
            PatternType patternType, String sequenceTypeName) {
        this.input = input;
        this.output = output;
        this.patternType = patternType;
        this.sequenceTypeName = sequenceTypeName;

        warnings = new ArrayList<>();
    }

    protected void copyStreams(final BufferedReader in, final PipedInputStream clonedPipe) throws SequenceParserException {

        final PipedOutputStream inPipe;
        try {
            inPipe = new PipedOutputStream(clonedPipe);
        } catch (IOException ex) {
            return;
        }

        Thread copy = new Thread(
                new Runnable() {
                    public void run() {

                        char[] buffer = new char[1024];
                        int length;
                        readLength = 0;

                        while (true) {
                            try {
                                // this is probably faster than XMLValidation
                                // so make sure there is no overflow 
                                if (clonedPipe.available() > MAX_BUFFER_SIZE) {
                                    continue;
                                }

                                length = in.read(buffer);
                                if (length != -1) {

                                    readLength += length;

                                    // add newly read to clone buffer and output
                                    String buff = new String(buffer, 0, length);

                                    inPipe.write(buff.getBytes());
                                    output.write(buff);

                                } else {
                                    // end of stream, close this thread
                                    inPipe.close();
                                    return;
                                }
                            } catch (IOException ex) {
                                try {
                                    // try at least to close the pipes
                                    inPipe.close();
                                    clonedPipe.close();
                                    return;
                                } catch (IOException ex1) {
                                    return;
                                }
                            }
                        }
                    }
                });
        copy.setUncaughtExceptionHandler(null);
        copy.start();
    }

    /**
     * Adds the string to the warnings.
     *
     * @param info
     */
    protected void logWarning(String info) {
        warnings.add(info);
    }

    /**
     * Return all warnings generated by parser.
     *
     * @return
     */
    @Override
    public List<String> getWarnings() {
        return warnings;
    }

    @Override
    abstract public void parseAndValidate() throws SequenceParserException, ForcedAbortOfPartValidation;

    @Override
    public void setMaximumCharsToValidate(int x) {
        maxLength = x;
    }

    protected void checkLength() throws ForcedAbortOfPartValidation {
        if (maxLength > 0 && readLength >= maxLength) {
            throw new ForcedAbortOfPartValidation();
        }
    }
}