/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 BiBiServ Curator Team, http://bibiserv.cebitec.uni-bielefeld.de,
 * All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common
 * Development and Distribution License("CDDL") (the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at http://www.sun.com/cddl/cddl.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.  When distributing the software, include
 * this License Header Notice in each file.  If applicable, add the following
 * below the License Header, with the fields enclosed by brackets [] replaced
 *  by your own identifying information:
 *
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 */
package de.unibi.cebitec.bibiserv.util.validate.impl;

import de.unibi.cebitec.bibiserv.util.validate.SequenceValidator.CARDINALITY;
import de.unibi.cebitec.bibiserv.util.validate.SequenceValidator.CONTENT;
import de.unibi.cebitec.bibiserv.util.validate.SequenceValidator.STRICTNESS;
import org.junit.Test;
import de.unibi.cebitec.bibiserv.util.validate.ValidationResult;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 * Tests for all GDE validators;
 *
 * Make sure that directory 'testdata' is within classpath!
 *
 * @author mrumming
 */
public class GDE_ValidatorTest extends AbstractTest{

    private GDE_Validator validator;
    private String gde_aa_single_flat;
    private String gde_dna_single_flat;
    private String gde_rna_single_flat;
    private String gde_aa_multi_flat;
    private String gde_dna_multi_flat;
    private String gde_rna_multi_flat;
    private String gde_aa_alignment_flat;
    private String gde_dna_alignment_flat;
    private String gde_rna_alignment_flat;
    private String gde_aa_single_asn1;
    private String gde_dna_single_asn1;
    private String gde_rna_single_asn1;
    private String gde_aa_multi_asn1;
    private String gde_dna_multi_asn1;
    private String gde_rna_multi_asn1;
    private String gde_aa_alignment_asn1;
    private String gde_dna_alignment_asn1;
    private String gde_rna_alignment_asn1;
    private String gde_aa_mixed;
    private String gde_dna_mixed;
    private String gde_rna_mixed;
    private String gde_single_broken_1;
    private String gde_single_broken_2;
    private String gde_multi_broken;
    private String gde_mixed_broken;
    private String fasta_aa_single;

    public GDE_ValidatorTest() {
        try {
            gde_aa_single_flat = readFromResource("/gde_aa_single_flat.gde");
            gde_dna_single_flat = readFromResource("/gde_dna_single_flat.gde");
            gde_rna_single_flat = readFromResource("/gde_rna_single_flat.gde");
            gde_aa_multi_flat = readFromResource("/gde_aa_multi_flat.gde");
            gde_dna_multi_flat = readFromResource("/gde_dna_multi_flat.gde");
            gde_rna_multi_flat = readFromResource("/gde_rna_multi_flat.gde");
            gde_aa_alignment_flat = readFromResource("/gde_aa_alignment_flat.gde");
            gde_dna_alignment_flat = readFromResource("/gde_dna_alignment_flat.gde");
            gde_rna_alignment_flat = readFromResource("/gde_rna_alignment_flat.gde");
            gde_aa_single_asn1 = readFromResource("/gde_aa_single_asn1.gde");
            gde_dna_single_asn1 = readFromResource("/gde_dna_single_asn1.gde");
            gde_rna_single_asn1 = readFromResource("/gde_rna_single_asn1.gde");
            gde_aa_multi_asn1 = readFromResource("/gde_aa_multi_asn1.gde");
            gde_dna_multi_asn1 = readFromResource("/gde_dna_multi_asn1.gde");
            gde_rna_multi_asn1 = readFromResource("/gde_rna_multi_asn1.gde");
            gde_aa_alignment_asn1 = readFromResource("/gde_aa_alignment_asn1.gde");
            gde_dna_alignment_asn1 = readFromResource("/gde_dna_alignment_asn1.gde");
            gde_rna_alignment_asn1 = readFromResource("/gde_rna_alignment_asn1.gde");
            gde_aa_mixed = readFromResource("/gde_aa_mixed.gde");
            gde_dna_mixed = readFromResource("/gde_dna_mixed.gde");
            gde_rna_mixed = readFromResource("/gde_rna_mixed.gde");
            gde_single_broken_1 = readFromResource("/gde_single_broken_1.gde");
            gde_single_broken_2 = readFromResource("/gde_single_broken_2.gde");
            gde_multi_broken = readFromResource("/gde_multi_broken.gde");
            gde_mixed_broken = readFromResource("/gde_mixed_broken.gde");
            
            fasta_aa_single = readFromResource("/fasta_aa_single.fas");
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGRA() {
        validator = new GDE_Validator();
        validator.setContent(CONTENT.RNA);
        validator.setAlignment(true);
        validator.setStrictness(STRICTNESS.strict);

        ValidationResult vr = validator.validateThis(this.gde_aa_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_aa_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_mixed_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_multi_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_2);
        assertTrue(vr.getMessage(), !vr.isValid());
    }

    @Test
    public void testGDA() {
        validator = new GDE_Validator();
        validator.setContent(CONTENT.DNA);
        validator.setAlignment(true);
        validator.setStrictness(STRICTNESS.strict);

        ValidationResult vr = validator.validateThis(this.gde_aa_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_mixed_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_multi_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_2);
        assertTrue(vr.getMessage(), !vr.isValid());
    }

    @Test
    public void testGAA() {
        validator = new GDE_Validator();
        validator.setContent(CONTENT.AA);
        validator.setAlignment(true);
        validator.setStrictness(STRICTNESS.strict);

        ValidationResult vr = validator.validateThis(this.gde_aa_alignment_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_aa_alignment_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_aa_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_mixed_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_multi_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_2);
        assertTrue(vr.getMessage(), !vr.isValid());
    }

    @Test
    public void testGAM() {
        validator = new GDE_Validator();
        validator.setContent(CONTENT.AA);
        validator.setCardinality(CARDINALITY.multi);
        validator.setStrictness(STRICTNESS.strict);
        ValidationResult vr = validator.validateThis(this.gde_aa_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_mixed);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_mixed);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_mixed);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_mixed_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_multi_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_2);
        assertTrue(vr.getMessage(), !vr.isValid());
    }

    @Test
    public void testGAS() {
        validator = new GDE_Validator();
        validator.setContent(CONTENT.AA);
        validator.setCardinality(CARDINALITY.single);
        validator.setStrictness(STRICTNESS.strict);
        ValidationResult vr = validator.validateThis(this.gde_aa_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_mixed_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_multi_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_2);
        assertTrue(vr.getMessage(), !vr.isValid());


    }

    @Test
    public void testGDM() {
        validator = new GDE_Validator();
        validator.setContent(CONTENT.DNA);
        validator.setCardinality(CARDINALITY.multi);
        validator.setStrictness(STRICTNESS.strict);
        ValidationResult vr = validator.validateThis(this.gde_aa_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_mixed);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_mixed_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_multi_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_2);
        assertTrue(vr.getMessage(), !vr.isValid());
    }

    @Test
    public void testGDS() {
        validator = new GDE_Validator();
        validator.setContent(CONTENT.DNA);
        validator.setCardinality(CARDINALITY.single);
        validator.setStrictness(STRICTNESS.strict);
        ValidationResult vr = validator.validateThis(this.gde_aa_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_mixed_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_multi_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_2);
        assertTrue(vr.getMessage(), !vr.isValid());
    }

    @Test
    public void testGRM() {
        validator = new GDE_Validator();
        validator.setContent(CONTENT.RNA);
        validator.setCardinality(CARDINALITY.multi);
        validator.setStrictness(STRICTNESS.strict);
        ValidationResult vr = validator.validateThis(this.gde_aa_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_mixed);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_mixed_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_multi_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_2);
        assertTrue(vr.getMessage(), !vr.isValid());

    }

    @Test
    public void testGRS() {
        validator = new GDE_Validator();
        validator.setContent(CONTENT.RNA);
        validator.setCardinality(CARDINALITY.single);
        validator.setStrictness(STRICTNESS.strict);
        ValidationResult vr = validator.validateThis(this.gde_aa_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_alignment_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_aa_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_dna_single_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_mixed);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_asn1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_multi_flat);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_flat);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_rna_single_asn1);
        assertTrue(vr.getMessage(), vr.isValid());
        vr = validator.validateThis(this.gde_mixed_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_multi_broken);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_1);
        assertTrue(vr.getMessage(), !vr.isValid());
        vr = validator.validateThis(this.gde_single_broken_2);
        assertTrue(vr.getMessage(), !vr.isValid());

    }
    
    @Test
    public void testWrongFormat() {
     validator = new GDE_Validator();
        validator.setContent(CONTENT.AA);
        validator.setCardinality(CARDINALITY.single);
        validator.setStrictness(STRICTNESS.strict);
        ValidationResult vr = validator.validateThis(fasta_aa_single);
        assertTrue(vr.getMessage(), !vr.isValid());
    }

}
