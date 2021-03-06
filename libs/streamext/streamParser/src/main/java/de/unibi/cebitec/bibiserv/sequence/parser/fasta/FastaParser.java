package de.unibi.cebitec.bibiserv.sequence.parser.fasta;

import de.unibi.cebitec.bibiserv.sequence.parser.AbstractSequenceParser;
import de.unibi.cebitec.bibiserv.sequence.parser.ForcedAbortOfPartValidation;
import de.unibi.cebitec.bibiserv.sequence.parser.ReadLineMessage;
import de.unibi.cebitec.bibiserv.sequence.parser.SequenceParserException;
import de.unibi.cebitec.bibiserv.sequenceparser.tools.PatternType;
import de.unibi.cebitec.bibiserv.sequenceparser.tools.SequenceValidator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * The parser to read all fasta types.
 *
 * @author Thomas Gatter - tgatter(at)cebitec.uni-bielefeld.de
 */
public class FastaParser extends AbstractSequenceParser {

    private FastaParserState state;
    private Set<String> ids;
    private String headerTmp;
    private String sequenceTmp;
    private int sequenceLength;
    private boolean lastSequenceRead;
    private static final int SEQUENCE_WIDTH = 80;
    /**
     * constants for lineBegin, lineTmp and lineEnd
     */
    private static final int COMMENT_LINE = 1;
    private static final int HEADER = 2;
    private static final int SEQUENCE = 3;

    /**
     * Inherit the super constructor.
     */
    public FastaParser(BufferedReader input, BufferedWriter output,
            PatternType patternType) {
        super(input, output, patternType);
        ids = new HashSet<String>();
        headerTmp = "";
        sequenceTmp = "";
        lastSequenceRead = false;
    }

    @Override
    public int parseAndValidateNextBlock() throws SequenceParserException, ForcedAbortOfPartValidation {

        // is there another possible sequence?
        if (lastSequenceRead) {
            return -1;
        }

        sequenceLength = 0;
        state = FastaParserState.seekSequenceStart;

        ReadLineMessage m;
        while ((m = readLine()) == ReadLineMessage.lineRead) {
            // just do it, validation is called automatically
        }
        if (state != FastaParserState.readSequence) {
            throw new SequenceParserException("No sequence was found!");
        }

        if (m == ReadLineMessage.noMoreLines) {
            lastSequenceRead = true;
        }
        writeSequence(sequenceTmp, true);
        sequenceTmp = "";

        if (sequenceLength == 0) {
            throw new SequenceParserException("No sequence-data for an id!");
        }

        return sequenceLength;
    }

    @Override
    protected int lineBeginSize() {
        // only one char is needed for fasta (; or >)
        return 1;
    }

    @Override
    protected void lineEmpty() {
        if (state == FastaParserState.readSequence) {
            logWarning("Empty line at line " + lineNumber + " ignored.");
        }
    }

    @Override
    protected int lineBegin(String value) throws SequenceParserException {

        // comment line
        if (value.startsWith(";")) {
            return COMMENT_LINE;
        }

        switch (state) {
            case seekSequenceStart:
                if (value.startsWith(">")) {
                    state = FastaParserState.readSequence;
                    return HEADER;
                }
                throw new SequenceParserException("Header needed but found something else on line " + lineNumber + ".");
            case readSequence:
                if (value.startsWith(">")) {
                    return END_READLINE;
                }
                return SEQUENCE;
        }
        return SEQUENCE; // never reached
    }

    @Override
    protected void lineTmp(int ident, String value) throws SequenceParserException {
        switch (ident) {
            case HEADER:
                // add up header for id extraction at end of line
                headerTmp += value;
                break;
            case SEQUENCE:
                // validate sequence
                if (!SequenceValidator.validate(patternType, value)) {
                    throw new SequenceParserException("Data was erroneous, did not correctly validate as " + patternType.name());
                }
                ;
                sequenceLength += value.length();
                sequenceTmp += value;
                sequenceTmp = writeSequence(sequenceTmp, false);
                break;
        }
    }

    @Override
    protected void lineEnd(int ident, String value) throws SequenceParserException {
        switch (ident) {
            case HEADER:
                // add up header for id extraction
                headerTmp += value;
                // extract id and test for double
                testHeader(headerTmp);

                // write to output
                write(headerTmp);
                writeNewLine();
                headerTmp = "";
                break;
            case SEQUENCE:
                // validate sequence
                if (!value.isEmpty() && !SequenceValidator.validate(patternType, value)) {
                    throw new SequenceParserException("Data was erroneous, did not correctly validate as " + patternType.name() + " : " + value);
                }

                sequenceLength += value.length();
                sequenceTmp += value;
                sequenceTmp = writeSequence(sequenceTmp, false);
                break;
            case COMMENT_LINE:
                logWarning("Ignored comment on line " + lineNumber + ".");
                break;
        }

    }

    private void testHeader(String header) throws SequenceParserException {
        String id = header.substring(1).split("\\s")[0];
        if (id.isEmpty()) {
            throw new SequenceParserException("Empty Id on line " + lineNumber + ".");
        }
        if (ids.contains(id)) {
            throw new SequenceParserException("Id '" + id + "' exists more than once.");
        }
        ids.add(id);
    }

    private String writeSequence(String sequence, boolean finalize) throws SequenceParserException {
        while (sequence.length() > SEQUENCE_WIDTH) {
            write(sequence.substring(0, SEQUENCE_WIDTH));
            writeNewLine();
            sequence = sequence.substring(SEQUENCE_WIDTH);
        }
        if (finalize) {
            write(sequence);
            writeNewLine();
        }
        return sequence;
    }
}
