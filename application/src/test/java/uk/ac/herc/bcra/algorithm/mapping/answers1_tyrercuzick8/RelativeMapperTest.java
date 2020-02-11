package uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import uk.ac.herc.bcra.algorithm.answers.version1.ResponseAccess;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.Parent;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.Relative;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionType;

public class RelativeMapperTest {

    private final String participantIdentifier = "TEST_PARTICIPANT_IDENTIFIER";

    @Test
    public void testMotherBreast() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_MOTHER,
            34,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE,
            null,

            null,
            null,
            null,
            null,
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        expected.mother.breastCancer = true;
        expected.mother.breastAge = 34;

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    public void testSisterBreast() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_SISTER,
            47,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE,
            null,

            null,
            null,
            null,
            null,
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Parent sister = new Parent();
        sister.breastCancer = true;
        sister.breastAge = 47;        
        expected.sisters.add(sister);

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);
    }


    @Test
    public void testMaternalGrandmotherBreast() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_GRANDMOTHER,
            65,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE,
            null,

            QuestionSectionIdentifier.FAMILY_AFFECTED_GRANDMOTHER,
            QuestionIdentifier.FAMILY_AFFECTED_GRANDMOTHER_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER,
            true,
            
            QuestionIdentifier.FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE,
            43
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);    
        expected.maternalGrandmother.breastCancer = true;
        expected.maternalGrandmother.breastAge = 65;
        expected.mother.breastCancer = false;
        expected.mother.breastAge = 43;

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);                   
    }

    @Test
    public void testPaternalGrandmotherBreast() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_GRANDMOTHER,
            81,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE,
            null,

            QuestionSectionIdentifier.FAMILY_AFFECTED_GRANDMOTHER,
            QuestionIdentifier.FAMILY_AFFECTED_GRANDMOTHER_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER,
            false,
            
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);    
        expected.paternalGrandmother.breastCancer = true;
        expected.paternalGrandmother.breastAge = 81;

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);  
    }    
    
    @Test
    public void testMaternalAuntBreast() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_AUNT,
            54,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE,
            null,

            QuestionSectionIdentifier.FAMILY_AFFECTED_AUNT,
            QuestionIdentifier.FAMILY_AFFECTED_AUNT_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_AUNT_SIDE_MOTHER,
            true,
            
            QuestionIdentifier.FAMILY_AFFECTED_AUNT_MOTHERS_AGE,
            37
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Parent maternalAunt = new Parent();
        maternalAunt.breastCancer = true;
        maternalAunt.breastAge = 54;
        expected.maternalAunts.add(maternalAunt);
        expected.mother.breastCancer = false;
        expected.mother.breastAge = 37;

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);          
    }

    @Test
    public void testPaternalAuntBreast() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_AUNT,
            73,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE,
            null,

            QuestionSectionIdentifier.FAMILY_AFFECTED_AUNT,
            QuestionIdentifier.FAMILY_AFFECTED_AUNT_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_AUNT_SIDE_MOTHER,
            false,
            
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Parent paternalAunt = new Parent();
        paternalAunt.breastCancer = true;
        paternalAunt.breastAge = 73;
        expected.paternalAunts.add(paternalAunt);

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);
    }   
    
    @Test
    public void testMaternalHalfsisterBreast() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_HALFSISTER,
            35,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE,
            null,

            QuestionSectionIdentifier.FAMILY_AFFECTED_HALF_SISTER,
            QuestionIdentifier.FAMILY_AFFECTED_HALFSISTER_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_HALFSISTER_SIDE_MOTHER,
            true,
            
            QuestionIdentifier.FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE,
            79
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Relative maternalHalfSister = new Relative();
        maternalHalfSister.breastCancer = true;
        maternalHalfSister.breastAge = 35;
        expected.maternalHalfSisters.add(maternalHalfSister);
        expected.mother.breastCancer = false;
        expected.mother.breastAge = 79;

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);         
    }

    @Test
    public void testPaternalHalfsisterBreast() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_HALFSISTER,
            28,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE,
            null,

            QuestionSectionIdentifier.FAMILY_AFFECTED_HALF_SISTER,
            QuestionIdentifier.FAMILY_AFFECTED_HALFSISTER_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_HALFSISTER_SIDE_MOTHER,
            false,
            
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Relative paternalHalfSister = new Relative();
        paternalHalfSister.breastCancer = true;
        paternalHalfSister.breastAge = 28;
        expected.paternalHalfSisters.add(paternalHalfSister);

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);   
    }   

    @Test
    public void testSisterNieceBreast() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NIECE,
            24,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE,
            null,

            QuestionSectionIdentifier.FAMILY_AFFECTED_NIECE,
            QuestionIdentifier.FAMILY_AFFECTED_NIECE_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_NIECE_SIDE_SISTER,
            true,
            
            QuestionIdentifier.FAMILY_AFFECTED_NIECE_SISTERS_AGE,
            46
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Parent sister = new Parent();
        sister.breastCancer = false;
        sister.breastAge = 46;
        Relative niece = new Relative();
        niece.breastCancer = true;
        niece.breastAge = 24;
        sister.daughters.add(niece);
        expected.sisters.add(sister);

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);             
    }

    @Test
    public void testPaternalNieceBreast() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NIECE,
            42,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE,
            null,

            QuestionSectionIdentifier.FAMILY_AFFECTED_NIECE,
            QuestionIdentifier.FAMILY_AFFECTED_NIECE_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_NIECE_SIDE_SISTER,
            false,
            
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Relative niece = new Relative();
        niece.breastCancer = true;
        niece.breastAge = 42;
        expected.unaffectedBrother.daughters.add(niece);

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);
    }      
    
    @Test
    public void testMotherOvarian() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE,
            null,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_MOTHER,
            51,

            null,
            null,
            null,
            null,
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        expected.mother.ovarianCancer = true;
        expected.mother.ovarianAge = 51;

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    public void testSisterOvarian() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE,
            null,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_SISTER,
            38,

            null,
            null,
            null,
            null,
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Parent sister = new Parent();
        sister.ovarianCancer = true;
        sister.ovarianAge = 38;        
        expected.sisters.add(sister);

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    public void testMaternalGrandmotherOvarian() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE,
            null,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_GRANDMOTHER,
            74,

            QuestionSectionIdentifier.FAMILY_AFFECTED_GRANDMOTHER,
            QuestionIdentifier.FAMILY_AFFECTED_GRANDMOTHER_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER,
            true,
            
            QuestionIdentifier.FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE,
            43
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);    
        expected.maternalGrandmother.ovarianCancer = true;
        expected.maternalGrandmother.ovarianAge = 74;
        expected.mother.breastCancer = false;
        expected.mother.breastAge = 43;

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);              
    }

    @Test
    public void testPaternalGrandmotherOvarian() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE,
            null,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_GRANDMOTHER,
            85,

            QuestionSectionIdentifier.FAMILY_AFFECTED_GRANDMOTHER,
            QuestionIdentifier.FAMILY_AFFECTED_GRANDMOTHER_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER,
            false,
            
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);    
        expected.paternalGrandmother.ovarianCancer = true;
        expected.paternalGrandmother.ovarianAge = 85;

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);  
    }  

    @Test
    public void testMaternalAuntOvarian() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE,
            null,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_AUNT,
            62,

            QuestionSectionIdentifier.FAMILY_AFFECTED_AUNT,
            QuestionIdentifier.FAMILY_AFFECTED_AUNT_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_AUNT_SIDE_MOTHER,
            true,
            
            QuestionIdentifier.FAMILY_AFFECTED_AUNT_MOTHERS_AGE,
            59
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Parent maternalAunt = new Parent();
        maternalAunt.ovarianCancer = true;
        maternalAunt.ovarianAge = 62;
        expected.maternalAunts.add(maternalAunt);
        expected.mother.breastCancer = false;
        expected.mother.breastAge = 59;

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);              
    }

    @Test
    public void testPaternalAuntOvarian() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE,
            null,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_AUNT,
            77,

            QuestionSectionIdentifier.FAMILY_AFFECTED_AUNT,
            QuestionIdentifier.FAMILY_AFFECTED_AUNT_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_AUNT_SIDE_MOTHER,
            false,
            
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Parent paternalAunt = new Parent();
        paternalAunt.ovarianCancer = true;
        paternalAunt.ovarianAge = 77;
        expected.paternalAunts.add(paternalAunt);

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    public void testMaternalHalfsisterOvarian() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE,
            null,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_HALFSISTER,
            29,

            QuestionSectionIdentifier.FAMILY_AFFECTED_HALF_SISTER,
            QuestionIdentifier.FAMILY_AFFECTED_HALFSISTER_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_HALFSISTER_SIDE_MOTHER,
            true,
            
            QuestionIdentifier.FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE,
            54
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Relative maternalHalfSister = new Relative();
        maternalHalfSister.ovarianCancer = true;
        maternalHalfSister.ovarianAge = 29;
        expected.maternalHalfSisters.add(maternalHalfSister);
        expected.mother.breastCancer = false;
        expected.mother.breastAge = 54;

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);                
    }    

    @Test
    public void testPaternalHalfsisterOvarian() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE,
            null,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_HALFSISTER,
            35,

            QuestionSectionIdentifier.FAMILY_AFFECTED_HALF_SISTER,
            QuestionIdentifier.FAMILY_AFFECTED_HALFSISTER_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_HALFSISTER_SIDE_MOTHER,
            false,
            
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Relative paternalHalfSister = new Relative();
        paternalHalfSister.ovarianCancer = true;
        paternalHalfSister.ovarianAge = 35;
        expected.paternalHalfSisters.add(paternalHalfSister);

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);   
    }  

    @Test
    public void testSisterNieceOvarian() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE,
            null,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NIECE,
            19,

            QuestionSectionIdentifier.FAMILY_AFFECTED_NIECE,
            QuestionIdentifier.FAMILY_AFFECTED_NIECE_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_NIECE_SIDE_SISTER,
            true,
            
            QuestionIdentifier.FAMILY_AFFECTED_NIECE_SISTERS_AGE,
            52
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Parent sister = new Parent();
        sister.breastCancer = false;
        sister.breastAge = 52;
        Relative niece = new Relative();
        niece.ovarianCancer = true;
        niece.ovarianAge = 19;
        sister.daughters.add(niece);
        expected.sisters.add(sister);

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);            
    }

    @Test
    public void testPaternalNieceOvarian() throws Exception {

        ResponseAccess response = generateRelativeResponse(
            QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE,
            null,

            QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NIECE,
            22,

            QuestionSectionIdentifier.FAMILY_AFFECTED_NIECE,
            QuestionIdentifier.FAMILY_AFFECTED_NIECE_SIDE,
            QuestionItemIdentifier.FAMILY_AFFECTED_NIECE_SIDE_SISTER,
            false,
            
            null,
            null
        );

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        RelativeMapper.map(response, algorithm);

        AlgorithmModel expected = new AlgorithmModel(participantIdentifier);
        Relative niece = new Relative();
        niece.ovarianCancer = true;
        niece.ovarianAge = 22;
        expected.unaffectedBrother.daughters.add(niece);

        assertThat(algorithm).isEqualToComparingFieldByFieldRecursively(expected);
    }      

    private ResponseAccess generateRelativeResponse(
        QuestionItemIdentifier breastItem,
        Integer breastAge,

        QuestionItemIdentifier ovarianItem,
        Integer ovarianAge,

        QuestionSectionIdentifier relativeSection,
        QuestionIdentifier sideQuestion,
        QuestionItemIdentifier femaleSideItem,
        Boolean femaleSide,
        
        QuestionIdentifier womanAgeQuestion,
        Integer womanAge
    ) {
        AnswerResponse response = new AnswerResponse();

        // FAMILY_BREAST_AFFECTED
        AnswerGroup breastAffectedGroup = new AnswerGroup();

        Map<QuestionItemIdentifier, Boolean> breastAffectedItems 
            = new HashMap<QuestionItemIdentifier, Boolean>();

        breastAffectedItems.put(breastItem, true);

        if (breastItem != QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_MOTHER) {
            breastAffectedItems.put(
                QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_MOTHER,
                false
            );
        }

        if (breastItem != QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE) {
            breastAffectedItems.put(
                QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE,
                false
            );
        }

        MapperTestHelper.addAnswerAndItems(
            breastAffectedGroup,
            QuestionIdentifier.FAMILY_BREAST_AFFECTED,
            QuestionType.CHECKBOX,
            breastAffectedItems
        );

        response.addAnswerSection(
            MapperTestHelper.createAnswerSection(
                QuestionSectionIdentifier.FAMILY_BREAST_AFFECTED,
                breastAffectedGroup
            )
        );
        
        // FAMILY_BREAST_HOW_MANY
        AnswerGroup breastHowManyGroup = new AnswerGroup();

        MapperTestHelper.addAnswerAndItem(
            breastHowManyGroup,
            QuestionIdentifier.FAMILY_BREAST_HOW_MANY,
            QuestionType.CHECKBOX,
            QuestionItemIdentifier.FAMILY_BREAST_HOW_MANY_ONE,
            true
        );
        
        response.addAnswerSection(
            MapperTestHelper.createAnswerSection(
                QuestionSectionIdentifier.FAMILY_BREAST_HOW_MANY,
                breastHowManyGroup
            )
        );

        // FAMILY_BREAST_AGE        
        AnswerGroup breastAgeGroup = new AnswerGroup();

        MapperTestHelper.addAnswerNumber(
            breastAgeGroup,
            QuestionIdentifier.FAMILY_BREAST_AGE,
            QuestionType.NUMBER,
            breastAge
        );

        response.addAnswerSection(
            MapperTestHelper.createAnswerSection(
                QuestionSectionIdentifier.FAMILY_BREAST_AGE,
                breastAgeGroup
            )
        );

        // FAMILY_OVARIAN_AFFECTED
        AnswerGroup ovarianAffectedGroup = new AnswerGroup();

        Map<QuestionItemIdentifier, Boolean> ovarianAffectedItems 
            = new HashMap<QuestionItemIdentifier, Boolean>();

        ovarianAffectedItems.put(ovarianItem, true);

        if (ovarianItem != QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_MOTHER) {
            ovarianAffectedItems.put(
                QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_MOTHER,
                false
            );
        }

        if (ovarianItem != QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE) {
            ovarianAffectedItems.put(
                QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE,
                false
            );
        }

        MapperTestHelper.addAnswerAndItems(
            ovarianAffectedGroup,
            QuestionIdentifier.FAMILY_OVARIAN_AFFECTED,
            QuestionType.CHECKBOX,
            ovarianAffectedItems
        );

        response.addAnswerSection(
            MapperTestHelper.createAnswerSection(
                QuestionSectionIdentifier.FAMILY_OVARIAN_AFFECTED,
                ovarianAffectedGroup
            )
        );
        
        // FAMILY_OVARIAN_HOW_MANY
        AnswerGroup ovarianHowManyGroup = new AnswerGroup();

        MapperTestHelper.addAnswerAndItem(
            ovarianHowManyGroup,
            QuestionIdentifier.FAMILY_OVARIAN_HOW_MANY,
            QuestionType.CHECKBOX,
            QuestionItemIdentifier.FAMILY_OVARIAN_HOW_MANY_ONE,
            true
        );
        
        response.addAnswerSection(
            MapperTestHelper.createAnswerSection(
                QuestionSectionIdentifier.FAMILY_OVARIAN_HOW_MANY,
                ovarianHowManyGroup
            )
        );

        // FAMILY_OVARIAN_AGE        
        AnswerGroup ovarianAgeGroup = new AnswerGroup();

        MapperTestHelper.addAnswerNumber(
            ovarianAgeGroup,
            QuestionIdentifier.FAMILY_OVARIAN_AGE,
            QuestionType.NUMBER,
            ovarianAge
        );

        response.addAnswerSection(
            MapperTestHelper.createAnswerSection(
                QuestionSectionIdentifier.FAMILY_OVARIAN_AGE,
                ovarianAgeGroup
            )
        );

        if (relativeSection != null) {

            // FAMILY_AFFECTED_<RELATIVE>_SIDE
            AnswerGroup relativeGroup = new AnswerGroup();

            MapperTestHelper.addAnswerAndItem(
                relativeGroup,
                sideQuestion,
                QuestionType.CHECKBOX,
                femaleSideItem,
                femaleSide
            );
            
            // FAMILY_AFFECTED_<RELATIVE>_[MOTHERS/SISTERS]_AGE
            MapperTestHelper.addAnswerNumber(
                relativeGroup,
                womanAgeQuestion,
                QuestionType.NUMBER,
                womanAge
            );

            response.addAnswerSection(
                MapperTestHelper.createAnswerSection(
                    relativeSection,
                    relativeGroup
                )
            );
        }

        return new ResponseAccess(response);
    }
}
