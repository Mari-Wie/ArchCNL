package org.architecture.cnl.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.architecture.cnl.services.ArchcnlGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalArchcnlParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_INT", "RULE_RELATION_NAME", "RULE_STRING", "RULE_NAME", "RULE_VARIABLE_NAME", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'Every'", "'No'", "'can'", "'.'", "'anything'", "'Nothing'", "'If'", "','", "'then'", "'it'", "'must'", "'this'", "'Only'", "'be'", "'can-only'", "'at-most'", "'at-least'", "'exactly'", "'and'", "'or'", "'equal-to'", "'a'", "'an'", "'that'", "'('", "')'", "'is'"
    };
    public static final int RULE_NAME=7;
    public static final int RULE_STRING=6;
    public static final int RULE_SL_COMMENT=11;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__37=37;
    public static final int T__16=16;
    public static final int T__38=38;
    public static final int T__17=17;
    public static final int T__39=39;
    public static final int T__18=18;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__14=14;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_RELATION_NAME=5;
    public static final int RULE_ID=9;
    public static final int RULE_WS=12;
    public static final int RULE_ANY_OTHER=13;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=4;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=10;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__40=40;
    public static final int RULE_VARIABLE_NAME=8;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalArchcnlParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalArchcnlParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalArchcnlParser.tokenNames; }
    public String getGrammarFileName() { return "InternalArchcnl.g"; }



     	private ArchcnlGrammarAccess grammarAccess;

        public InternalArchcnlParser(TokenStream input, ArchcnlGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Model";
       	}

       	@Override
       	protected ArchcnlGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleModel"
    // InternalArchcnl.g:64:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalArchcnl.g:64:46: (iv_ruleModel= ruleModel EOF )
            // InternalArchcnl.g:65:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalArchcnl.g:71:1: ruleModel returns [EObject current=null] : ( (lv_sentence_0_0= ruleSentence ) )* ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject lv_sentence_0_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:77:2: ( ( (lv_sentence_0_0= ruleSentence ) )* )
            // InternalArchcnl.g:78:2: ( (lv_sentence_0_0= ruleSentence ) )*
            {
            // InternalArchcnl.g:78:2: ( (lv_sentence_0_0= ruleSentence ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_NAME||(LA1_0>=14 && LA1_0<=15)||(LA1_0>=19 && LA1_0<=20)||LA1_0==26) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalArchcnl.g:79:3: (lv_sentence_0_0= ruleSentence )
            	    {
            	    // InternalArchcnl.g:79:3: (lv_sentence_0_0= ruleSentence )
            	    // InternalArchcnl.g:80:4: lv_sentence_0_0= ruleSentence
            	    {

            	    				newCompositeNode(grammarAccess.getModelAccess().getSentenceSentenceParserRuleCall_0());
            	    			
            	    pushFollow(FOLLOW_3);
            	    lv_sentence_0_0=ruleSentence();

            	    state._fsp--;


            	    				if (current==null) {
            	    					current = createModelElementForParent(grammarAccess.getModelRule());
            	    				}
            	    				add(
            	    					current,
            	    					"sentence",
            	    					lv_sentence_0_0,
            	    					"org.architecture.cnl.Archcnl.Sentence");
            	    				afterParserOrEnumRuleCall();
            	    			

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleSentence"
    // InternalArchcnl.g:100:1: entryRuleSentence returns [EObject current=null] : iv_ruleSentence= ruleSentence EOF ;
    public final EObject entryRuleSentence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSentence = null;


        try {
            // InternalArchcnl.g:100:49: (iv_ruleSentence= ruleSentence EOF )
            // InternalArchcnl.g:101:2: iv_ruleSentence= ruleSentence EOF
            {
             newCompositeNode(grammarAccess.getSentenceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSentence=ruleSentence();

            state._fsp--;

             current =iv_ruleSentence; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSentence"


    // $ANTLR start "ruleSentence"
    // InternalArchcnl.g:107:1: ruleSentence returns [EObject current=null] : ( (otherlv_0= 'Every' ( (lv_subject_1_0= ruleConceptExpression ) ) ( ( (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType ) ) ) ) | ( ( (lv_ruletype_3_1= ruleOnlyCanRuleType | lv_ruletype_3_2= ruleConditionalRuleType | lv_ruletype_3_3= ruleNegationRuleType ) ) ) | ( (lv_facts_4_0= ruleFactStatement ) ) ) ;
    public final EObject ruleSentence() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_subject_1_0 = null;

        EObject lv_ruletype_2_1 = null;

        EObject lv_ruletype_2_2 = null;

        EObject lv_ruletype_2_3 = null;

        EObject lv_ruletype_2_4 = null;

        EObject lv_ruletype_3_1 = null;

        EObject lv_ruletype_3_2 = null;

        EObject lv_ruletype_3_3 = null;

        EObject lv_facts_4_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:113:2: ( ( (otherlv_0= 'Every' ( (lv_subject_1_0= ruleConceptExpression ) ) ( ( (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType ) ) ) ) | ( ( (lv_ruletype_3_1= ruleOnlyCanRuleType | lv_ruletype_3_2= ruleConditionalRuleType | lv_ruletype_3_3= ruleNegationRuleType ) ) ) | ( (lv_facts_4_0= ruleFactStatement ) ) ) )
            // InternalArchcnl.g:114:2: ( (otherlv_0= 'Every' ( (lv_subject_1_0= ruleConceptExpression ) ) ( ( (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType ) ) ) ) | ( ( (lv_ruletype_3_1= ruleOnlyCanRuleType | lv_ruletype_3_2= ruleConditionalRuleType | lv_ruletype_3_3= ruleNegationRuleType ) ) ) | ( (lv_facts_4_0= ruleFactStatement ) ) )
            {
            // InternalArchcnl.g:114:2: ( (otherlv_0= 'Every' ( (lv_subject_1_0= ruleConceptExpression ) ) ( ( (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType ) ) ) ) | ( ( (lv_ruletype_3_1= ruleOnlyCanRuleType | lv_ruletype_3_2= ruleConditionalRuleType | lv_ruletype_3_3= ruleNegationRuleType ) ) ) | ( (lv_facts_4_0= ruleFactStatement ) ) )
            int alt4=3;
            switch ( input.LA(1) ) {
            case 14:
                {
                alt4=1;
                }
                break;
            case 15:
            case 19:
            case 20:
            case 26:
                {
                alt4=2;
                }
                break;
            case RULE_NAME:
                {
                alt4=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalArchcnl.g:115:3: (otherlv_0= 'Every' ( (lv_subject_1_0= ruleConceptExpression ) ) ( ( (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType ) ) ) )
                    {
                    // InternalArchcnl.g:115:3: (otherlv_0= 'Every' ( (lv_subject_1_0= ruleConceptExpression ) ) ( ( (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType ) ) ) )
                    // InternalArchcnl.g:116:4: otherlv_0= 'Every' ( (lv_subject_1_0= ruleConceptExpression ) ) ( ( (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType ) ) )
                    {
                    otherlv_0=(Token)match(input,14,FOLLOW_4); 

                    				newLeafNode(otherlv_0, grammarAccess.getSentenceAccess().getEveryKeyword_0_0());
                    			
                    // InternalArchcnl.g:120:4: ( (lv_subject_1_0= ruleConceptExpression ) )
                    // InternalArchcnl.g:121:5: (lv_subject_1_0= ruleConceptExpression )
                    {
                    // InternalArchcnl.g:121:5: (lv_subject_1_0= ruleConceptExpression )
                    // InternalArchcnl.g:122:6: lv_subject_1_0= ruleConceptExpression
                    {

                    						newCompositeNode(grammarAccess.getSentenceAccess().getSubjectConceptExpressionParserRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_5);
                    lv_subject_1_0=ruleConceptExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getSentenceRule());
                    						}
                    						set(
                    							current,
                    							"subject",
                    							lv_subject_1_0,
                    							"org.architecture.cnl.Archcnl.ConceptExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalArchcnl.g:139:4: ( ( (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType ) ) )
                    // InternalArchcnl.g:140:5: ( (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType ) )
                    {
                    // InternalArchcnl.g:140:5: ( (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType ) )
                    // InternalArchcnl.g:141:6: (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType )
                    {
                    // InternalArchcnl.g:141:6: (lv_ruletype_2_1= ruleMustRuleType | lv_ruletype_2_2= ruleCanOnlyRuleType | lv_ruletype_2_3= ruleCardinalityRuleType | lv_ruletype_2_4= ruleSubConceptRuleType )
                    int alt2=4;
                    switch ( input.LA(1) ) {
                    case 24:
                        {
                        int LA2_1 = input.LA(2);

                        if ( (LA2_1==RULE_RELATION_NAME) ) {
                            alt2=1;
                        }
                        else if ( (LA2_1==27) ) {
                            alt2=4;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 2, 1, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 28:
                        {
                        alt2=2;
                        }
                        break;
                    case 16:
                        {
                        alt2=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 0, input);

                        throw nvae;
                    }

                    switch (alt2) {
                        case 1 :
                            // InternalArchcnl.g:142:7: lv_ruletype_2_1= ruleMustRuleType
                            {

                            							newCompositeNode(grammarAccess.getSentenceAccess().getRuletypeMustRuleTypeParserRuleCall_0_2_0_0());
                            						
                            pushFollow(FOLLOW_2);
                            lv_ruletype_2_1=ruleMustRuleType();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getSentenceRule());
                            							}
                            							set(
                            								current,
                            								"ruletype",
                            								lv_ruletype_2_1,
                            								"org.architecture.cnl.Archcnl.MustRuleType");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;
                        case 2 :
                            // InternalArchcnl.g:158:7: lv_ruletype_2_2= ruleCanOnlyRuleType
                            {

                            							newCompositeNode(grammarAccess.getSentenceAccess().getRuletypeCanOnlyRuleTypeParserRuleCall_0_2_0_1());
                            						
                            pushFollow(FOLLOW_2);
                            lv_ruletype_2_2=ruleCanOnlyRuleType();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getSentenceRule());
                            							}
                            							set(
                            								current,
                            								"ruletype",
                            								lv_ruletype_2_2,
                            								"org.architecture.cnl.Archcnl.CanOnlyRuleType");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;
                        case 3 :
                            // InternalArchcnl.g:174:7: lv_ruletype_2_3= ruleCardinalityRuleType
                            {

                            							newCompositeNode(grammarAccess.getSentenceAccess().getRuletypeCardinalityRuleTypeParserRuleCall_0_2_0_2());
                            						
                            pushFollow(FOLLOW_2);
                            lv_ruletype_2_3=ruleCardinalityRuleType();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getSentenceRule());
                            							}
                            							set(
                            								current,
                            								"ruletype",
                            								lv_ruletype_2_3,
                            								"org.architecture.cnl.Archcnl.CardinalityRuleType");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;
                        case 4 :
                            // InternalArchcnl.g:190:7: lv_ruletype_2_4= ruleSubConceptRuleType
                            {

                            							newCompositeNode(grammarAccess.getSentenceAccess().getRuletypeSubConceptRuleTypeParserRuleCall_0_2_0_3());
                            						
                            pushFollow(FOLLOW_2);
                            lv_ruletype_2_4=ruleSubConceptRuleType();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getSentenceRule());
                            							}
                            							set(
                            								current,
                            								"ruletype",
                            								lv_ruletype_2_4,
                            								"org.architecture.cnl.Archcnl.SubConceptRuleType");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;

                    }


                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:210:3: ( ( (lv_ruletype_3_1= ruleOnlyCanRuleType | lv_ruletype_3_2= ruleConditionalRuleType | lv_ruletype_3_3= ruleNegationRuleType ) ) )
                    {
                    // InternalArchcnl.g:210:3: ( ( (lv_ruletype_3_1= ruleOnlyCanRuleType | lv_ruletype_3_2= ruleConditionalRuleType | lv_ruletype_3_3= ruleNegationRuleType ) ) )
                    // InternalArchcnl.g:211:4: ( (lv_ruletype_3_1= ruleOnlyCanRuleType | lv_ruletype_3_2= ruleConditionalRuleType | lv_ruletype_3_3= ruleNegationRuleType ) )
                    {
                    // InternalArchcnl.g:211:4: ( (lv_ruletype_3_1= ruleOnlyCanRuleType | lv_ruletype_3_2= ruleConditionalRuleType | lv_ruletype_3_3= ruleNegationRuleType ) )
                    // InternalArchcnl.g:212:5: (lv_ruletype_3_1= ruleOnlyCanRuleType | lv_ruletype_3_2= ruleConditionalRuleType | lv_ruletype_3_3= ruleNegationRuleType )
                    {
                    // InternalArchcnl.g:212:5: (lv_ruletype_3_1= ruleOnlyCanRuleType | lv_ruletype_3_2= ruleConditionalRuleType | lv_ruletype_3_3= ruleNegationRuleType )
                    int alt3=3;
                    switch ( input.LA(1) ) {
                    case 26:
                        {
                        alt3=1;
                        }
                        break;
                    case 20:
                        {
                        alt3=2;
                        }
                        break;
                    case 15:
                    case 19:
                        {
                        alt3=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 0, input);

                        throw nvae;
                    }

                    switch (alt3) {
                        case 1 :
                            // InternalArchcnl.g:213:6: lv_ruletype_3_1= ruleOnlyCanRuleType
                            {

                            						newCompositeNode(grammarAccess.getSentenceAccess().getRuletypeOnlyCanRuleTypeParserRuleCall_1_0_0());
                            					
                            pushFollow(FOLLOW_2);
                            lv_ruletype_3_1=ruleOnlyCanRuleType();

                            state._fsp--;


                            						if (current==null) {
                            							current = createModelElementForParent(grammarAccess.getSentenceRule());
                            						}
                            						set(
                            							current,
                            							"ruletype",
                            							lv_ruletype_3_1,
                            							"org.architecture.cnl.Archcnl.OnlyCanRuleType");
                            						afterParserOrEnumRuleCall();
                            					

                            }
                            break;
                        case 2 :
                            // InternalArchcnl.g:229:6: lv_ruletype_3_2= ruleConditionalRuleType
                            {

                            						newCompositeNode(grammarAccess.getSentenceAccess().getRuletypeConditionalRuleTypeParserRuleCall_1_0_1());
                            					
                            pushFollow(FOLLOW_2);
                            lv_ruletype_3_2=ruleConditionalRuleType();

                            state._fsp--;


                            						if (current==null) {
                            							current = createModelElementForParent(grammarAccess.getSentenceRule());
                            						}
                            						set(
                            							current,
                            							"ruletype",
                            							lv_ruletype_3_2,
                            							"org.architecture.cnl.Archcnl.ConditionalRuleType");
                            						afterParserOrEnumRuleCall();
                            					

                            }
                            break;
                        case 3 :
                            // InternalArchcnl.g:245:6: lv_ruletype_3_3= ruleNegationRuleType
                            {

                            						newCompositeNode(grammarAccess.getSentenceAccess().getRuletypeNegationRuleTypeParserRuleCall_1_0_2());
                            					
                            pushFollow(FOLLOW_2);
                            lv_ruletype_3_3=ruleNegationRuleType();

                            state._fsp--;


                            						if (current==null) {
                            							current = createModelElementForParent(grammarAccess.getSentenceRule());
                            						}
                            						set(
                            							current,
                            							"ruletype",
                            							lv_ruletype_3_3,
                            							"org.architecture.cnl.Archcnl.NegationRuleType");
                            						afterParserOrEnumRuleCall();
                            					

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalArchcnl.g:264:3: ( (lv_facts_4_0= ruleFactStatement ) )
                    {
                    // InternalArchcnl.g:264:3: ( (lv_facts_4_0= ruleFactStatement ) )
                    // InternalArchcnl.g:265:4: (lv_facts_4_0= ruleFactStatement )
                    {
                    // InternalArchcnl.g:265:4: (lv_facts_4_0= ruleFactStatement )
                    // InternalArchcnl.g:266:5: lv_facts_4_0= ruleFactStatement
                    {

                    					newCompositeNode(grammarAccess.getSentenceAccess().getFactsFactStatementParserRuleCall_2_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_facts_4_0=ruleFactStatement();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getSentenceRule());
                    					}
                    					add(
                    						current,
                    						"facts",
                    						lv_facts_4_0,
                    						"org.architecture.cnl.Archcnl.FactStatement");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSentence"


    // $ANTLR start "entryRuleNegationRuleType"
    // InternalArchcnl.g:287:1: entryRuleNegationRuleType returns [EObject current=null] : iv_ruleNegationRuleType= ruleNegationRuleType EOF ;
    public final EObject entryRuleNegationRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNegationRuleType = null;


        try {
            // InternalArchcnl.g:287:57: (iv_ruleNegationRuleType= ruleNegationRuleType EOF )
            // InternalArchcnl.g:288:2: iv_ruleNegationRuleType= ruleNegationRuleType EOF
            {
             newCompositeNode(grammarAccess.getNegationRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNegationRuleType=ruleNegationRuleType();

            state._fsp--;

             current =iv_ruleNegationRuleType; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNegationRuleType"


    // $ANTLR start "ruleNegationRuleType"
    // InternalArchcnl.g:294:1: ruleNegationRuleType returns [EObject current=null] : (this_Nothing_0= ruleNothing | ( (otherlv_1= 'No' ( (lv_subject_2_0= ruleConceptExpression ) ) otherlv_3= 'can' ( (lv_object_4_0= ruleObject ) ) ) otherlv_5= '.' ) ) ;
    public final EObject ruleNegationRuleType() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject this_Nothing_0 = null;

        EObject lv_subject_2_0 = null;

        EObject lv_object_4_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:300:2: ( (this_Nothing_0= ruleNothing | ( (otherlv_1= 'No' ( (lv_subject_2_0= ruleConceptExpression ) ) otherlv_3= 'can' ( (lv_object_4_0= ruleObject ) ) ) otherlv_5= '.' ) ) )
            // InternalArchcnl.g:301:2: (this_Nothing_0= ruleNothing | ( (otherlv_1= 'No' ( (lv_subject_2_0= ruleConceptExpression ) ) otherlv_3= 'can' ( (lv_object_4_0= ruleObject ) ) ) otherlv_5= '.' ) )
            {
            // InternalArchcnl.g:301:2: (this_Nothing_0= ruleNothing | ( (otherlv_1= 'No' ( (lv_subject_2_0= ruleConceptExpression ) ) otherlv_3= 'can' ( (lv_object_4_0= ruleObject ) ) ) otherlv_5= '.' ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==19) ) {
                alt5=1;
            }
            else if ( (LA5_0==15) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalArchcnl.g:302:3: this_Nothing_0= ruleNothing
                    {

                    			newCompositeNode(grammarAccess.getNegationRuleTypeAccess().getNothingParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Nothing_0=ruleNothing();

                    state._fsp--;


                    			current = this_Nothing_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:311:3: ( (otherlv_1= 'No' ( (lv_subject_2_0= ruleConceptExpression ) ) otherlv_3= 'can' ( (lv_object_4_0= ruleObject ) ) ) otherlv_5= '.' )
                    {
                    // InternalArchcnl.g:311:3: ( (otherlv_1= 'No' ( (lv_subject_2_0= ruleConceptExpression ) ) otherlv_3= 'can' ( (lv_object_4_0= ruleObject ) ) ) otherlv_5= '.' )
                    // InternalArchcnl.g:312:4: (otherlv_1= 'No' ( (lv_subject_2_0= ruleConceptExpression ) ) otherlv_3= 'can' ( (lv_object_4_0= ruleObject ) ) ) otherlv_5= '.'
                    {
                    // InternalArchcnl.g:312:4: (otherlv_1= 'No' ( (lv_subject_2_0= ruleConceptExpression ) ) otherlv_3= 'can' ( (lv_object_4_0= ruleObject ) ) )
                    // InternalArchcnl.g:313:5: otherlv_1= 'No' ( (lv_subject_2_0= ruleConceptExpression ) ) otherlv_3= 'can' ( (lv_object_4_0= ruleObject ) )
                    {
                    otherlv_1=(Token)match(input,15,FOLLOW_4); 

                    					newLeafNode(otherlv_1, grammarAccess.getNegationRuleTypeAccess().getNoKeyword_1_0_0());
                    				
                    // InternalArchcnl.g:317:5: ( (lv_subject_2_0= ruleConceptExpression ) )
                    // InternalArchcnl.g:318:6: (lv_subject_2_0= ruleConceptExpression )
                    {
                    // InternalArchcnl.g:318:6: (lv_subject_2_0= ruleConceptExpression )
                    // InternalArchcnl.g:319:7: lv_subject_2_0= ruleConceptExpression
                    {

                    							newCompositeNode(grammarAccess.getNegationRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0_1_0());
                    						
                    pushFollow(FOLLOW_6);
                    lv_subject_2_0=ruleConceptExpression();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getNegationRuleTypeRule());
                    							}
                    							set(
                    								current,
                    								"subject",
                    								lv_subject_2_0,
                    								"org.architecture.cnl.Archcnl.ConceptExpression");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }

                    otherlv_3=(Token)match(input,16,FOLLOW_7); 

                    					newLeafNode(otherlv_3, grammarAccess.getNegationRuleTypeAccess().getCanKeyword_1_0_2());
                    				
                    // InternalArchcnl.g:340:5: ( (lv_object_4_0= ruleObject ) )
                    // InternalArchcnl.g:341:6: (lv_object_4_0= ruleObject )
                    {
                    // InternalArchcnl.g:341:6: (lv_object_4_0= ruleObject )
                    // InternalArchcnl.g:342:7: lv_object_4_0= ruleObject
                    {

                    							newCompositeNode(grammarAccess.getNegationRuleTypeAccess().getObjectObjectParserRuleCall_1_0_3_0());
                    						
                    pushFollow(FOLLOW_8);
                    lv_object_4_0=ruleObject();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getNegationRuleTypeRule());
                    							}
                    							set(
                    								current,
                    								"object",
                    								lv_object_4_0,
                    								"org.architecture.cnl.Archcnl.Object");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }

                    otherlv_5=(Token)match(input,17,FOLLOW_2); 

                    				newLeafNode(otherlv_5, grammarAccess.getNegationRuleTypeAccess().getFullStopKeyword_1_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNegationRuleType"


    // $ANTLR start "entryRuleAnything"
    // InternalArchcnl.g:369:1: entryRuleAnything returns [EObject current=null] : iv_ruleAnything= ruleAnything EOF ;
    public final EObject entryRuleAnything() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnything = null;


        try {
            // InternalArchcnl.g:369:49: (iv_ruleAnything= ruleAnything EOF )
            // InternalArchcnl.g:370:2: iv_ruleAnything= ruleAnything EOF
            {
             newCompositeNode(grammarAccess.getAnythingRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAnything=ruleAnything();

            state._fsp--;

             current =iv_ruleAnything; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnything"


    // $ANTLR start "ruleAnything"
    // InternalArchcnl.g:376:1: ruleAnything returns [EObject current=null] : ( ( (lv_relation_0_0= ruleRelation ) ) otherlv_1= 'anything' ) ;
    public final EObject ruleAnything() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_relation_0_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:382:2: ( ( ( (lv_relation_0_0= ruleRelation ) ) otherlv_1= 'anything' ) )
            // InternalArchcnl.g:383:2: ( ( (lv_relation_0_0= ruleRelation ) ) otherlv_1= 'anything' )
            {
            // InternalArchcnl.g:383:2: ( ( (lv_relation_0_0= ruleRelation ) ) otherlv_1= 'anything' )
            // InternalArchcnl.g:384:3: ( (lv_relation_0_0= ruleRelation ) ) otherlv_1= 'anything'
            {
            // InternalArchcnl.g:384:3: ( (lv_relation_0_0= ruleRelation ) )
            // InternalArchcnl.g:385:4: (lv_relation_0_0= ruleRelation )
            {
            // InternalArchcnl.g:385:4: (lv_relation_0_0= ruleRelation )
            // InternalArchcnl.g:386:5: lv_relation_0_0= ruleRelation
            {

            					newCompositeNode(grammarAccess.getAnythingAccess().getRelationRelationParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_9);
            lv_relation_0_0=ruleRelation();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAnythingRule());
            					}
            					set(
            						current,
            						"relation",
            						lv_relation_0_0,
            						"org.architecture.cnl.Archcnl.Relation");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,18,FOLLOW_2); 

            			newLeafNode(otherlv_1, grammarAccess.getAnythingAccess().getAnythingKeyword_1());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnything"


    // $ANTLR start "entryRuleNothing"
    // InternalArchcnl.g:411:1: entryRuleNothing returns [EObject current=null] : iv_ruleNothing= ruleNothing EOF ;
    public final EObject entryRuleNothing() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNothing = null;


        try {
            // InternalArchcnl.g:411:48: (iv_ruleNothing= ruleNothing EOF )
            // InternalArchcnl.g:412:2: iv_ruleNothing= ruleNothing EOF
            {
             newCompositeNode(grammarAccess.getNothingRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNothing=ruleNothing();

            state._fsp--;

             current =iv_ruleNothing; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNothing"


    // $ANTLR start "ruleNothing"
    // InternalArchcnl.g:418:1: ruleNothing returns [EObject current=null] : (otherlv_0= 'Nothing' otherlv_1= 'can' ( (lv_object_2_0= ruleObject ) ) otherlv_3= '.' ) ;
    public final EObject ruleNothing() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_object_2_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:424:2: ( (otherlv_0= 'Nothing' otherlv_1= 'can' ( (lv_object_2_0= ruleObject ) ) otherlv_3= '.' ) )
            // InternalArchcnl.g:425:2: (otherlv_0= 'Nothing' otherlv_1= 'can' ( (lv_object_2_0= ruleObject ) ) otherlv_3= '.' )
            {
            // InternalArchcnl.g:425:2: (otherlv_0= 'Nothing' otherlv_1= 'can' ( (lv_object_2_0= ruleObject ) ) otherlv_3= '.' )
            // InternalArchcnl.g:426:3: otherlv_0= 'Nothing' otherlv_1= 'can' ( (lv_object_2_0= ruleObject ) ) otherlv_3= '.'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getNothingAccess().getNothingKeyword_0());
            		
            otherlv_1=(Token)match(input,16,FOLLOW_7); 

            			newLeafNode(otherlv_1, grammarAccess.getNothingAccess().getCanKeyword_1());
            		
            // InternalArchcnl.g:434:3: ( (lv_object_2_0= ruleObject ) )
            // InternalArchcnl.g:435:4: (lv_object_2_0= ruleObject )
            {
            // InternalArchcnl.g:435:4: (lv_object_2_0= ruleObject )
            // InternalArchcnl.g:436:5: lv_object_2_0= ruleObject
            {

            					newCompositeNode(grammarAccess.getNothingAccess().getObjectObjectParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_8);
            lv_object_2_0=ruleObject();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getNothingRule());
            					}
            					set(
            						current,
            						"object",
            						lv_object_2_0,
            						"org.architecture.cnl.Archcnl.Object");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getNothingAccess().getFullStopKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNothing"


    // $ANTLR start "entryRuleConditionalRuleType"
    // InternalArchcnl.g:461:1: entryRuleConditionalRuleType returns [EObject current=null] : iv_ruleConditionalRuleType= ruleConditionalRuleType EOF ;
    public final EObject entryRuleConditionalRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionalRuleType = null;


        try {
            // InternalArchcnl.g:461:60: (iv_ruleConditionalRuleType= ruleConditionalRuleType EOF )
            // InternalArchcnl.g:462:2: iv_ruleConditionalRuleType= ruleConditionalRuleType EOF
            {
             newCompositeNode(grammarAccess.getConditionalRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConditionalRuleType=ruleConditionalRuleType();

            state._fsp--;

             current =iv_ruleConditionalRuleType; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConditionalRuleType"


    // $ANTLR start "ruleConditionalRuleType"
    // InternalArchcnl.g:468:1: ruleConditionalRuleType returns [EObject current=null] : ( ( (lv_start_0_0= 'If' ) ) ( (lv_subject_1_0= ruleConceptExpression ) ) ( (lv_relation_2_0= ruleRelation ) ) ( (lv_object_3_0= ruleConceptExpression ) ) otherlv_4= ',' otherlv_5= 'then' otherlv_6= 'it' otherlv_7= 'must' ( (lv_relation2_8_0= ruleRelation ) ) otherlv_9= 'this' ( (lv_object2_10_0= ruleConceptExpression ) ) otherlv_11= '.' ) ;
    public final EObject ruleConditionalRuleType() throws RecognitionException {
        EObject current = null;

        Token lv_start_0_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        EObject lv_subject_1_0 = null;

        EObject lv_relation_2_0 = null;

        EObject lv_object_3_0 = null;

        EObject lv_relation2_8_0 = null;

        EObject lv_object2_10_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:474:2: ( ( ( (lv_start_0_0= 'If' ) ) ( (lv_subject_1_0= ruleConceptExpression ) ) ( (lv_relation_2_0= ruleRelation ) ) ( (lv_object_3_0= ruleConceptExpression ) ) otherlv_4= ',' otherlv_5= 'then' otherlv_6= 'it' otherlv_7= 'must' ( (lv_relation2_8_0= ruleRelation ) ) otherlv_9= 'this' ( (lv_object2_10_0= ruleConceptExpression ) ) otherlv_11= '.' ) )
            // InternalArchcnl.g:475:2: ( ( (lv_start_0_0= 'If' ) ) ( (lv_subject_1_0= ruleConceptExpression ) ) ( (lv_relation_2_0= ruleRelation ) ) ( (lv_object_3_0= ruleConceptExpression ) ) otherlv_4= ',' otherlv_5= 'then' otherlv_6= 'it' otherlv_7= 'must' ( (lv_relation2_8_0= ruleRelation ) ) otherlv_9= 'this' ( (lv_object2_10_0= ruleConceptExpression ) ) otherlv_11= '.' )
            {
            // InternalArchcnl.g:475:2: ( ( (lv_start_0_0= 'If' ) ) ( (lv_subject_1_0= ruleConceptExpression ) ) ( (lv_relation_2_0= ruleRelation ) ) ( (lv_object_3_0= ruleConceptExpression ) ) otherlv_4= ',' otherlv_5= 'then' otherlv_6= 'it' otherlv_7= 'must' ( (lv_relation2_8_0= ruleRelation ) ) otherlv_9= 'this' ( (lv_object2_10_0= ruleConceptExpression ) ) otherlv_11= '.' )
            // InternalArchcnl.g:476:3: ( (lv_start_0_0= 'If' ) ) ( (lv_subject_1_0= ruleConceptExpression ) ) ( (lv_relation_2_0= ruleRelation ) ) ( (lv_object_3_0= ruleConceptExpression ) ) otherlv_4= ',' otherlv_5= 'then' otherlv_6= 'it' otherlv_7= 'must' ( (lv_relation2_8_0= ruleRelation ) ) otherlv_9= 'this' ( (lv_object2_10_0= ruleConceptExpression ) ) otherlv_11= '.'
            {
            // InternalArchcnl.g:476:3: ( (lv_start_0_0= 'If' ) )
            // InternalArchcnl.g:477:4: (lv_start_0_0= 'If' )
            {
            // InternalArchcnl.g:477:4: (lv_start_0_0= 'If' )
            // InternalArchcnl.g:478:5: lv_start_0_0= 'If'
            {
            lv_start_0_0=(Token)match(input,20,FOLLOW_4); 

            					newLeafNode(lv_start_0_0, grammarAccess.getConditionalRuleTypeAccess().getStartIfKeyword_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConditionalRuleTypeRule());
            					}
            					setWithLastConsumed(current, "start", lv_start_0_0, "If");
            				

            }


            }

            // InternalArchcnl.g:490:3: ( (lv_subject_1_0= ruleConceptExpression ) )
            // InternalArchcnl.g:491:4: (lv_subject_1_0= ruleConceptExpression )
            {
            // InternalArchcnl.g:491:4: (lv_subject_1_0= ruleConceptExpression )
            // InternalArchcnl.g:492:5: lv_subject_1_0= ruleConceptExpression
            {

            					newCompositeNode(grammarAccess.getConditionalRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_7);
            lv_subject_1_0=ruleConceptExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConditionalRuleTypeRule());
            					}
            					set(
            						current,
            						"subject",
            						lv_subject_1_0,
            						"org.architecture.cnl.Archcnl.ConceptExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalArchcnl.g:509:3: ( (lv_relation_2_0= ruleRelation ) )
            // InternalArchcnl.g:510:4: (lv_relation_2_0= ruleRelation )
            {
            // InternalArchcnl.g:510:4: (lv_relation_2_0= ruleRelation )
            // InternalArchcnl.g:511:5: lv_relation_2_0= ruleRelation
            {

            					newCompositeNode(grammarAccess.getConditionalRuleTypeAccess().getRelationRelationParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_4);
            lv_relation_2_0=ruleRelation();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConditionalRuleTypeRule());
            					}
            					set(
            						current,
            						"relation",
            						lv_relation_2_0,
            						"org.architecture.cnl.Archcnl.Relation");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalArchcnl.g:528:3: ( (lv_object_3_0= ruleConceptExpression ) )
            // InternalArchcnl.g:529:4: (lv_object_3_0= ruleConceptExpression )
            {
            // InternalArchcnl.g:529:4: (lv_object_3_0= ruleConceptExpression )
            // InternalArchcnl.g:530:5: lv_object_3_0= ruleConceptExpression
            {

            					newCompositeNode(grammarAccess.getConditionalRuleTypeAccess().getObjectConceptExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_10);
            lv_object_3_0=ruleConceptExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConditionalRuleTypeRule());
            					}
            					set(
            						current,
            						"object",
            						lv_object_3_0,
            						"org.architecture.cnl.Archcnl.ConceptExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,21,FOLLOW_11); 

            			newLeafNode(otherlv_4, grammarAccess.getConditionalRuleTypeAccess().getCommaKeyword_4());
            		
            otherlv_5=(Token)match(input,22,FOLLOW_12); 

            			newLeafNode(otherlv_5, grammarAccess.getConditionalRuleTypeAccess().getThenKeyword_5());
            		
            otherlv_6=(Token)match(input,23,FOLLOW_13); 

            			newLeafNode(otherlv_6, grammarAccess.getConditionalRuleTypeAccess().getItKeyword_6());
            		
            otherlv_7=(Token)match(input,24,FOLLOW_7); 

            			newLeafNode(otherlv_7, grammarAccess.getConditionalRuleTypeAccess().getMustKeyword_7());
            		
            // InternalArchcnl.g:563:3: ( (lv_relation2_8_0= ruleRelation ) )
            // InternalArchcnl.g:564:4: (lv_relation2_8_0= ruleRelation )
            {
            // InternalArchcnl.g:564:4: (lv_relation2_8_0= ruleRelation )
            // InternalArchcnl.g:565:5: lv_relation2_8_0= ruleRelation
            {

            					newCompositeNode(grammarAccess.getConditionalRuleTypeAccess().getRelation2RelationParserRuleCall_8_0());
            				
            pushFollow(FOLLOW_14);
            lv_relation2_8_0=ruleRelation();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConditionalRuleTypeRule());
            					}
            					set(
            						current,
            						"relation2",
            						lv_relation2_8_0,
            						"org.architecture.cnl.Archcnl.Relation");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_9=(Token)match(input,25,FOLLOW_4); 

            			newLeafNode(otherlv_9, grammarAccess.getConditionalRuleTypeAccess().getThisKeyword_9());
            		
            // InternalArchcnl.g:586:3: ( (lv_object2_10_0= ruleConceptExpression ) )
            // InternalArchcnl.g:587:4: (lv_object2_10_0= ruleConceptExpression )
            {
            // InternalArchcnl.g:587:4: (lv_object2_10_0= ruleConceptExpression )
            // InternalArchcnl.g:588:5: lv_object2_10_0= ruleConceptExpression
            {

            					newCompositeNode(grammarAccess.getConditionalRuleTypeAccess().getObject2ConceptExpressionParserRuleCall_10_0());
            				
            pushFollow(FOLLOW_8);
            lv_object2_10_0=ruleConceptExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConditionalRuleTypeRule());
            					}
            					set(
            						current,
            						"object2",
            						lv_object2_10_0,
            						"org.architecture.cnl.Archcnl.ConceptExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_11=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_11, grammarAccess.getConditionalRuleTypeAccess().getFullStopKeyword_11());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConditionalRuleType"


    // $ANTLR start "entryRuleOnlyCanRuleType"
    // InternalArchcnl.g:613:1: entryRuleOnlyCanRuleType returns [EObject current=null] : iv_ruleOnlyCanRuleType= ruleOnlyCanRuleType EOF ;
    public final EObject entryRuleOnlyCanRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOnlyCanRuleType = null;


        try {
            // InternalArchcnl.g:613:56: (iv_ruleOnlyCanRuleType= ruleOnlyCanRuleType EOF )
            // InternalArchcnl.g:614:2: iv_ruleOnlyCanRuleType= ruleOnlyCanRuleType EOF
            {
             newCompositeNode(grammarAccess.getOnlyCanRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOnlyCanRuleType=ruleOnlyCanRuleType();

            state._fsp--;

             current =iv_ruleOnlyCanRuleType; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOnlyCanRuleType"


    // $ANTLR start "ruleOnlyCanRuleType"
    // InternalArchcnl.g:620:1: ruleOnlyCanRuleType returns [EObject current=null] : ( ( (lv_start_0_0= 'Only' ) ) ( (lv_subject_1_0= ruleConceptExpression ) ) otherlv_2= 'can' ( (lv_object_3_0= ruleObject ) ) otherlv_4= '.' ) ;
    public final EObject ruleOnlyCanRuleType() throws RecognitionException {
        EObject current = null;

        Token lv_start_0_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_subject_1_0 = null;

        EObject lv_object_3_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:626:2: ( ( ( (lv_start_0_0= 'Only' ) ) ( (lv_subject_1_0= ruleConceptExpression ) ) otherlv_2= 'can' ( (lv_object_3_0= ruleObject ) ) otherlv_4= '.' ) )
            // InternalArchcnl.g:627:2: ( ( (lv_start_0_0= 'Only' ) ) ( (lv_subject_1_0= ruleConceptExpression ) ) otherlv_2= 'can' ( (lv_object_3_0= ruleObject ) ) otherlv_4= '.' )
            {
            // InternalArchcnl.g:627:2: ( ( (lv_start_0_0= 'Only' ) ) ( (lv_subject_1_0= ruleConceptExpression ) ) otherlv_2= 'can' ( (lv_object_3_0= ruleObject ) ) otherlv_4= '.' )
            // InternalArchcnl.g:628:3: ( (lv_start_0_0= 'Only' ) ) ( (lv_subject_1_0= ruleConceptExpression ) ) otherlv_2= 'can' ( (lv_object_3_0= ruleObject ) ) otherlv_4= '.'
            {
            // InternalArchcnl.g:628:3: ( (lv_start_0_0= 'Only' ) )
            // InternalArchcnl.g:629:4: (lv_start_0_0= 'Only' )
            {
            // InternalArchcnl.g:629:4: (lv_start_0_0= 'Only' )
            // InternalArchcnl.g:630:5: lv_start_0_0= 'Only'
            {
            lv_start_0_0=(Token)match(input,26,FOLLOW_4); 

            					newLeafNode(lv_start_0_0, grammarAccess.getOnlyCanRuleTypeAccess().getStartOnlyKeyword_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getOnlyCanRuleTypeRule());
            					}
            					setWithLastConsumed(current, "start", lv_start_0_0, "Only");
            				

            }


            }

            // InternalArchcnl.g:642:3: ( (lv_subject_1_0= ruleConceptExpression ) )
            // InternalArchcnl.g:643:4: (lv_subject_1_0= ruleConceptExpression )
            {
            // InternalArchcnl.g:643:4: (lv_subject_1_0= ruleConceptExpression )
            // InternalArchcnl.g:644:5: lv_subject_1_0= ruleConceptExpression
            {

            					newCompositeNode(grammarAccess.getOnlyCanRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_6);
            lv_subject_1_0=ruleConceptExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOnlyCanRuleTypeRule());
            					}
            					set(
            						current,
            						"subject",
            						lv_subject_1_0,
            						"org.architecture.cnl.Archcnl.ConceptExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,16,FOLLOW_7); 

            			newLeafNode(otherlv_2, grammarAccess.getOnlyCanRuleTypeAccess().getCanKeyword_2());
            		
            // InternalArchcnl.g:665:3: ( (lv_object_3_0= ruleObject ) )
            // InternalArchcnl.g:666:4: (lv_object_3_0= ruleObject )
            {
            // InternalArchcnl.g:666:4: (lv_object_3_0= ruleObject )
            // InternalArchcnl.g:667:5: lv_object_3_0= ruleObject
            {

            					newCompositeNode(grammarAccess.getOnlyCanRuleTypeAccess().getObjectObjectParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_8);
            lv_object_3_0=ruleObject();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOnlyCanRuleTypeRule());
            					}
            					set(
            						current,
            						"object",
            						lv_object_3_0,
            						"org.architecture.cnl.Archcnl.Object");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getOnlyCanRuleTypeAccess().getFullStopKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOnlyCanRuleType"


    // $ANTLR start "entryRuleSubConceptRuleType"
    // InternalArchcnl.g:692:1: entryRuleSubConceptRuleType returns [EObject current=null] : iv_ruleSubConceptRuleType= ruleSubConceptRuleType EOF ;
    public final EObject entryRuleSubConceptRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSubConceptRuleType = null;


        try {
            // InternalArchcnl.g:692:59: (iv_ruleSubConceptRuleType= ruleSubConceptRuleType EOF )
            // InternalArchcnl.g:693:2: iv_ruleSubConceptRuleType= ruleSubConceptRuleType EOF
            {
             newCompositeNode(grammarAccess.getSubConceptRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSubConceptRuleType=ruleSubConceptRuleType();

            state._fsp--;

             current =iv_ruleSubConceptRuleType; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSubConceptRuleType"


    // $ANTLR start "ruleSubConceptRuleType"
    // InternalArchcnl.g:699:1: ruleSubConceptRuleType returns [EObject current=null] : ( ( (lv_modifier_0_0= 'must' ) ) otherlv_1= 'be' ( (lv_object_2_0= ruleConceptExpression ) ) otherlv_3= '.' ) ;
    public final EObject ruleSubConceptRuleType() throws RecognitionException {
        EObject current = null;

        Token lv_modifier_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_object_2_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:705:2: ( ( ( (lv_modifier_0_0= 'must' ) ) otherlv_1= 'be' ( (lv_object_2_0= ruleConceptExpression ) ) otherlv_3= '.' ) )
            // InternalArchcnl.g:706:2: ( ( (lv_modifier_0_0= 'must' ) ) otherlv_1= 'be' ( (lv_object_2_0= ruleConceptExpression ) ) otherlv_3= '.' )
            {
            // InternalArchcnl.g:706:2: ( ( (lv_modifier_0_0= 'must' ) ) otherlv_1= 'be' ( (lv_object_2_0= ruleConceptExpression ) ) otherlv_3= '.' )
            // InternalArchcnl.g:707:3: ( (lv_modifier_0_0= 'must' ) ) otherlv_1= 'be' ( (lv_object_2_0= ruleConceptExpression ) ) otherlv_3= '.'
            {
            // InternalArchcnl.g:707:3: ( (lv_modifier_0_0= 'must' ) )
            // InternalArchcnl.g:708:4: (lv_modifier_0_0= 'must' )
            {
            // InternalArchcnl.g:708:4: (lv_modifier_0_0= 'must' )
            // InternalArchcnl.g:709:5: lv_modifier_0_0= 'must'
            {
            lv_modifier_0_0=(Token)match(input,24,FOLLOW_15); 

            					newLeafNode(lv_modifier_0_0, grammarAccess.getSubConceptRuleTypeAccess().getModifierMustKeyword_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSubConceptRuleTypeRule());
            					}
            					setWithLastConsumed(current, "modifier", lv_modifier_0_0, "must");
            				

            }


            }

            otherlv_1=(Token)match(input,27,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getSubConceptRuleTypeAccess().getBeKeyword_1());
            		
            // InternalArchcnl.g:725:3: ( (lv_object_2_0= ruleConceptExpression ) )
            // InternalArchcnl.g:726:4: (lv_object_2_0= ruleConceptExpression )
            {
            // InternalArchcnl.g:726:4: (lv_object_2_0= ruleConceptExpression )
            // InternalArchcnl.g:727:5: lv_object_2_0= ruleConceptExpression
            {

            					newCompositeNode(grammarAccess.getSubConceptRuleTypeAccess().getObjectConceptExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_8);
            lv_object_2_0=ruleConceptExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSubConceptRuleTypeRule());
            					}
            					set(
            						current,
            						"object",
            						lv_object_2_0,
            						"org.architecture.cnl.Archcnl.ConceptExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getSubConceptRuleTypeAccess().getFullStopKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSubConceptRuleType"


    // $ANTLR start "entryRuleMustRuleType"
    // InternalArchcnl.g:752:1: entryRuleMustRuleType returns [EObject current=null] : iv_ruleMustRuleType= ruleMustRuleType EOF ;
    public final EObject entryRuleMustRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMustRuleType = null;


        try {
            // InternalArchcnl.g:752:53: (iv_ruleMustRuleType= ruleMustRuleType EOF )
            // InternalArchcnl.g:753:2: iv_ruleMustRuleType= ruleMustRuleType EOF
            {
             newCompositeNode(grammarAccess.getMustRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMustRuleType=ruleMustRuleType();

            state._fsp--;

             current =iv_ruleMustRuleType; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMustRuleType"


    // $ANTLR start "ruleMustRuleType"
    // InternalArchcnl.g:759:1: ruleMustRuleType returns [EObject current=null] : ( ( (lv_modifier_0_0= 'must' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' ) ;
    public final EObject ruleMustRuleType() throws RecognitionException {
        EObject current = null;

        Token lv_modifier_0_0=null;
        Token otherlv_2=null;
        EObject lv_object_1_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:765:2: ( ( ( (lv_modifier_0_0= 'must' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' ) )
            // InternalArchcnl.g:766:2: ( ( (lv_modifier_0_0= 'must' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' )
            {
            // InternalArchcnl.g:766:2: ( ( (lv_modifier_0_0= 'must' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' )
            // InternalArchcnl.g:767:3: ( (lv_modifier_0_0= 'must' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.'
            {
            // InternalArchcnl.g:767:3: ( (lv_modifier_0_0= 'must' ) )
            // InternalArchcnl.g:768:4: (lv_modifier_0_0= 'must' )
            {
            // InternalArchcnl.g:768:4: (lv_modifier_0_0= 'must' )
            // InternalArchcnl.g:769:5: lv_modifier_0_0= 'must'
            {
            lv_modifier_0_0=(Token)match(input,24,FOLLOW_7); 

            					newLeafNode(lv_modifier_0_0, grammarAccess.getMustRuleTypeAccess().getModifierMustKeyword_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMustRuleTypeRule());
            					}
            					setWithLastConsumed(current, "modifier", lv_modifier_0_0, "must");
            				

            }


            }

            // InternalArchcnl.g:781:3: ( (lv_object_1_0= ruleObject ) )
            // InternalArchcnl.g:782:4: (lv_object_1_0= ruleObject )
            {
            // InternalArchcnl.g:782:4: (lv_object_1_0= ruleObject )
            // InternalArchcnl.g:783:5: lv_object_1_0= ruleObject
            {

            					newCompositeNode(grammarAccess.getMustRuleTypeAccess().getObjectObjectParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_8);
            lv_object_1_0=ruleObject();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMustRuleTypeRule());
            					}
            					set(
            						current,
            						"object",
            						lv_object_1_0,
            						"org.architecture.cnl.Archcnl.Object");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getMustRuleTypeAccess().getFullStopKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMustRuleType"


    // $ANTLR start "entryRuleObject"
    // InternalArchcnl.g:808:1: entryRuleObject returns [EObject current=null] : iv_ruleObject= ruleObject EOF ;
    public final EObject entryRuleObject() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleObject = null;


        try {
            // InternalArchcnl.g:808:47: (iv_ruleObject= ruleObject EOF )
            // InternalArchcnl.g:809:2: iv_ruleObject= ruleObject EOF
            {
             newCompositeNode(grammarAccess.getObjectRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleObject=ruleObject();

            state._fsp--;

             current =iv_ruleObject; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleObject"


    // $ANTLR start "ruleObject"
    // InternalArchcnl.g:815:1: ruleObject returns [EObject current=null] : ( ( (lv_anything_0_0= ruleAnything ) ) | ( ( (lv_expression_1_0= ruleObjectConceptExpression ) ) ( ( (lv_objectAndList_2_0= ruleAndObjectConceptExpression ) ) | ( (lv_objectOrList_3_0= ruleOrObjectConceptExpression ) ) )* ) ) ;
    public final EObject ruleObject() throws RecognitionException {
        EObject current = null;

        EObject lv_anything_0_0 = null;

        EObject lv_expression_1_0 = null;

        EObject lv_objectAndList_2_0 = null;

        EObject lv_objectOrList_3_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:821:2: ( ( ( (lv_anything_0_0= ruleAnything ) ) | ( ( (lv_expression_1_0= ruleObjectConceptExpression ) ) ( ( (lv_objectAndList_2_0= ruleAndObjectConceptExpression ) ) | ( (lv_objectOrList_3_0= ruleOrObjectConceptExpression ) ) )* ) ) )
            // InternalArchcnl.g:822:2: ( ( (lv_anything_0_0= ruleAnything ) ) | ( ( (lv_expression_1_0= ruleObjectConceptExpression ) ) ( ( (lv_objectAndList_2_0= ruleAndObjectConceptExpression ) ) | ( (lv_objectOrList_3_0= ruleOrObjectConceptExpression ) ) )* ) )
            {
            // InternalArchcnl.g:822:2: ( ( (lv_anything_0_0= ruleAnything ) ) | ( ( (lv_expression_1_0= ruleObjectConceptExpression ) ) ( ( (lv_objectAndList_2_0= ruleAndObjectConceptExpression ) ) | ( (lv_objectOrList_3_0= ruleOrObjectConceptExpression ) ) )* ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==RULE_RELATION_NAME) ) {
                switch ( input.LA(2) ) {
                case RULE_NAME:
                case 29:
                case 30:
                case 31:
                case 35:
                case 36:
                    {
                    alt7=2;
                    }
                    break;
                case 34:
                    {
                    int LA7_3 = input.LA(3);

                    if ( (LA7_3==RULE_INT||(LA7_3>=RULE_STRING && LA7_3<=RULE_NAME)||(LA7_3>=29 && LA7_3<=31)||(LA7_3>=35 && LA7_3<=36)) ) {
                        alt7=2;
                    }
                    else if ( (LA7_3==18) ) {
                        alt7=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case 18:
                    {
                    alt7=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // InternalArchcnl.g:823:3: ( (lv_anything_0_0= ruleAnything ) )
                    {
                    // InternalArchcnl.g:823:3: ( (lv_anything_0_0= ruleAnything ) )
                    // InternalArchcnl.g:824:4: (lv_anything_0_0= ruleAnything )
                    {
                    // InternalArchcnl.g:824:4: (lv_anything_0_0= ruleAnything )
                    // InternalArchcnl.g:825:5: lv_anything_0_0= ruleAnything
                    {

                    					newCompositeNode(grammarAccess.getObjectAccess().getAnythingAnythingParserRuleCall_0_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_anything_0_0=ruleAnything();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getObjectRule());
                    					}
                    					set(
                    						current,
                    						"anything",
                    						lv_anything_0_0,
                    						"org.architecture.cnl.Archcnl.Anything");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:843:3: ( ( (lv_expression_1_0= ruleObjectConceptExpression ) ) ( ( (lv_objectAndList_2_0= ruleAndObjectConceptExpression ) ) | ( (lv_objectOrList_3_0= ruleOrObjectConceptExpression ) ) )* )
                    {
                    // InternalArchcnl.g:843:3: ( ( (lv_expression_1_0= ruleObjectConceptExpression ) ) ( ( (lv_objectAndList_2_0= ruleAndObjectConceptExpression ) ) | ( (lv_objectOrList_3_0= ruleOrObjectConceptExpression ) ) )* )
                    // InternalArchcnl.g:844:4: ( (lv_expression_1_0= ruleObjectConceptExpression ) ) ( ( (lv_objectAndList_2_0= ruleAndObjectConceptExpression ) ) | ( (lv_objectOrList_3_0= ruleOrObjectConceptExpression ) ) )*
                    {
                    // InternalArchcnl.g:844:4: ( (lv_expression_1_0= ruleObjectConceptExpression ) )
                    // InternalArchcnl.g:845:5: (lv_expression_1_0= ruleObjectConceptExpression )
                    {
                    // InternalArchcnl.g:845:5: (lv_expression_1_0= ruleObjectConceptExpression )
                    // InternalArchcnl.g:846:6: lv_expression_1_0= ruleObjectConceptExpression
                    {

                    						newCompositeNode(grammarAccess.getObjectAccess().getExpressionObjectConceptExpressionParserRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_16);
                    lv_expression_1_0=ruleObjectConceptExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getObjectRule());
                    						}
                    						set(
                    							current,
                    							"expression",
                    							lv_expression_1_0,
                    							"org.architecture.cnl.Archcnl.ObjectConceptExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalArchcnl.g:863:4: ( ( (lv_objectAndList_2_0= ruleAndObjectConceptExpression ) ) | ( (lv_objectOrList_3_0= ruleOrObjectConceptExpression ) ) )*
                    loop6:
                    do {
                        int alt6=3;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==32) ) {
                            alt6=1;
                        }
                        else if ( (LA6_0==33) ) {
                            alt6=2;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // InternalArchcnl.g:864:5: ( (lv_objectAndList_2_0= ruleAndObjectConceptExpression ) )
                    	    {
                    	    // InternalArchcnl.g:864:5: ( (lv_objectAndList_2_0= ruleAndObjectConceptExpression ) )
                    	    // InternalArchcnl.g:865:6: (lv_objectAndList_2_0= ruleAndObjectConceptExpression )
                    	    {
                    	    // InternalArchcnl.g:865:6: (lv_objectAndList_2_0= ruleAndObjectConceptExpression )
                    	    // InternalArchcnl.g:866:7: lv_objectAndList_2_0= ruleAndObjectConceptExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getObjectAccess().getObjectAndListAndObjectConceptExpressionParserRuleCall_1_1_0_0());
                    	    						
                    	    pushFollow(FOLLOW_16);
                    	    lv_objectAndList_2_0=ruleAndObjectConceptExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getObjectRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"objectAndList",
                    	    								lv_objectAndList_2_0,
                    	    								"org.architecture.cnl.Archcnl.AndObjectConceptExpression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalArchcnl.g:884:5: ( (lv_objectOrList_3_0= ruleOrObjectConceptExpression ) )
                    	    {
                    	    // InternalArchcnl.g:884:5: ( (lv_objectOrList_3_0= ruleOrObjectConceptExpression ) )
                    	    // InternalArchcnl.g:885:6: (lv_objectOrList_3_0= ruleOrObjectConceptExpression )
                    	    {
                    	    // InternalArchcnl.g:885:6: (lv_objectOrList_3_0= ruleOrObjectConceptExpression )
                    	    // InternalArchcnl.g:886:7: lv_objectOrList_3_0= ruleOrObjectConceptExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getObjectAccess().getObjectOrListOrObjectConceptExpressionParserRuleCall_1_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_16);
                    	    lv_objectOrList_3_0=ruleOrObjectConceptExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getObjectRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"objectOrList",
                    	    								lv_objectOrList_3_0,
                    	    								"org.architecture.cnl.Archcnl.OrObjectConceptExpression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleObject"


    // $ANTLR start "entryRuleCanOnlyRuleType"
    // InternalArchcnl.g:909:1: entryRuleCanOnlyRuleType returns [EObject current=null] : iv_ruleCanOnlyRuleType= ruleCanOnlyRuleType EOF ;
    public final EObject entryRuleCanOnlyRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCanOnlyRuleType = null;


        try {
            // InternalArchcnl.g:909:56: (iv_ruleCanOnlyRuleType= ruleCanOnlyRuleType EOF )
            // InternalArchcnl.g:910:2: iv_ruleCanOnlyRuleType= ruleCanOnlyRuleType EOF
            {
             newCompositeNode(grammarAccess.getCanOnlyRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCanOnlyRuleType=ruleCanOnlyRuleType();

            state._fsp--;

             current =iv_ruleCanOnlyRuleType; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCanOnlyRuleType"


    // $ANTLR start "ruleCanOnlyRuleType"
    // InternalArchcnl.g:916:1: ruleCanOnlyRuleType returns [EObject current=null] : ( ( (lv_modifier_0_0= 'can-only' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' ) ;
    public final EObject ruleCanOnlyRuleType() throws RecognitionException {
        EObject current = null;

        Token lv_modifier_0_0=null;
        Token otherlv_2=null;
        EObject lv_object_1_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:922:2: ( ( ( (lv_modifier_0_0= 'can-only' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' ) )
            // InternalArchcnl.g:923:2: ( ( (lv_modifier_0_0= 'can-only' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' )
            {
            // InternalArchcnl.g:923:2: ( ( (lv_modifier_0_0= 'can-only' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' )
            // InternalArchcnl.g:924:3: ( (lv_modifier_0_0= 'can-only' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.'
            {
            // InternalArchcnl.g:924:3: ( (lv_modifier_0_0= 'can-only' ) )
            // InternalArchcnl.g:925:4: (lv_modifier_0_0= 'can-only' )
            {
            // InternalArchcnl.g:925:4: (lv_modifier_0_0= 'can-only' )
            // InternalArchcnl.g:926:5: lv_modifier_0_0= 'can-only'
            {
            lv_modifier_0_0=(Token)match(input,28,FOLLOW_7); 

            					newLeafNode(lv_modifier_0_0, grammarAccess.getCanOnlyRuleTypeAccess().getModifierCanOnlyKeyword_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCanOnlyRuleTypeRule());
            					}
            					setWithLastConsumed(current, "modifier", lv_modifier_0_0, "can-only");
            				

            }


            }

            // InternalArchcnl.g:938:3: ( (lv_object_1_0= ruleObject ) )
            // InternalArchcnl.g:939:4: (lv_object_1_0= ruleObject )
            {
            // InternalArchcnl.g:939:4: (lv_object_1_0= ruleObject )
            // InternalArchcnl.g:940:5: lv_object_1_0= ruleObject
            {

            					newCompositeNode(grammarAccess.getCanOnlyRuleTypeAccess().getObjectObjectParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_8);
            lv_object_1_0=ruleObject();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCanOnlyRuleTypeRule());
            					}
            					set(
            						current,
            						"object",
            						lv_object_1_0,
            						"org.architecture.cnl.Archcnl.Object");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getCanOnlyRuleTypeAccess().getFullStopKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCanOnlyRuleType"


    // $ANTLR start "entryRuleCardinalityRuleType"
    // InternalArchcnl.g:965:1: entryRuleCardinalityRuleType returns [EObject current=null] : iv_ruleCardinalityRuleType= ruleCardinalityRuleType EOF ;
    public final EObject entryRuleCardinalityRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCardinalityRuleType = null;


        try {
            // InternalArchcnl.g:965:60: (iv_ruleCardinalityRuleType= ruleCardinalityRuleType EOF )
            // InternalArchcnl.g:966:2: iv_ruleCardinalityRuleType= ruleCardinalityRuleType EOF
            {
             newCompositeNode(grammarAccess.getCardinalityRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCardinalityRuleType=ruleCardinalityRuleType();

            state._fsp--;

             current =iv_ruleCardinalityRuleType; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCardinalityRuleType"


    // $ANTLR start "ruleCardinalityRuleType"
    // InternalArchcnl.g:972:1: ruleCardinalityRuleType returns [EObject current=null] : ( ( (lv_modifer_0_0= 'can' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' ) ;
    public final EObject ruleCardinalityRuleType() throws RecognitionException {
        EObject current = null;

        Token lv_modifer_0_0=null;
        Token otherlv_2=null;
        EObject lv_object_1_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:978:2: ( ( ( (lv_modifer_0_0= 'can' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' ) )
            // InternalArchcnl.g:979:2: ( ( (lv_modifer_0_0= 'can' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' )
            {
            // InternalArchcnl.g:979:2: ( ( (lv_modifer_0_0= 'can' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.' )
            // InternalArchcnl.g:980:3: ( (lv_modifer_0_0= 'can' ) ) ( (lv_object_1_0= ruleObject ) ) otherlv_2= '.'
            {
            // InternalArchcnl.g:980:3: ( (lv_modifer_0_0= 'can' ) )
            // InternalArchcnl.g:981:4: (lv_modifer_0_0= 'can' )
            {
            // InternalArchcnl.g:981:4: (lv_modifer_0_0= 'can' )
            // InternalArchcnl.g:982:5: lv_modifer_0_0= 'can'
            {
            lv_modifer_0_0=(Token)match(input,16,FOLLOW_7); 

            					newLeafNode(lv_modifer_0_0, grammarAccess.getCardinalityRuleTypeAccess().getModiferCanKeyword_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCardinalityRuleTypeRule());
            					}
            					setWithLastConsumed(current, "modifer", lv_modifer_0_0, "can");
            				

            }


            }

            // InternalArchcnl.g:994:3: ( (lv_object_1_0= ruleObject ) )
            // InternalArchcnl.g:995:4: (lv_object_1_0= ruleObject )
            {
            // InternalArchcnl.g:995:4: (lv_object_1_0= ruleObject )
            // InternalArchcnl.g:996:5: lv_object_1_0= ruleObject
            {

            					newCompositeNode(grammarAccess.getCardinalityRuleTypeAccess().getObjectObjectParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_8);
            lv_object_1_0=ruleObject();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCardinalityRuleTypeRule());
            					}
            					set(
            						current,
            						"object",
            						lv_object_1_0,
            						"org.architecture.cnl.Archcnl.Object");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getCardinalityRuleTypeAccess().getFullStopKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCardinalityRuleType"


    // $ANTLR start "entryRuleObjectConceptExpression"
    // InternalArchcnl.g:1021:1: entryRuleObjectConceptExpression returns [EObject current=null] : iv_ruleObjectConceptExpression= ruleObjectConceptExpression EOF ;
    public final EObject entryRuleObjectConceptExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleObjectConceptExpression = null;


        try {
            // InternalArchcnl.g:1021:64: (iv_ruleObjectConceptExpression= ruleObjectConceptExpression EOF )
            // InternalArchcnl.g:1022:2: iv_ruleObjectConceptExpression= ruleObjectConceptExpression EOF
            {
             newCompositeNode(grammarAccess.getObjectConceptExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleObjectConceptExpression=ruleObjectConceptExpression();

            state._fsp--;

             current =iv_ruleObjectConceptExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleObjectConceptExpression"


    // $ANTLR start "ruleObjectConceptExpression"
    // InternalArchcnl.g:1028:1: ruleObjectConceptExpression returns [EObject current=null] : ( ( ( (lv_relation_0_0= ruleRelation ) ) ( ( ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) ) ) ( (lv_number_2_0= RULE_INT ) ) )? ( (lv_concept_3_0= ruleConceptExpression ) ) ) | ( ( (lv_relation_4_0= ruleDatatypeRelation ) ) ( (lv_data_5_0= ruleDataStatement ) ) ) ) ;
    public final EObject ruleObjectConceptExpression() throws RecognitionException {
        EObject current = null;

        Token lv_cardinality_1_1=null;
        Token lv_cardinality_1_2=null;
        Token lv_cardinality_1_3=null;
        Token lv_number_2_0=null;
        EObject lv_relation_0_0 = null;

        EObject lv_concept_3_0 = null;

        EObject lv_relation_4_0 = null;

        EObject lv_data_5_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1034:2: ( ( ( ( (lv_relation_0_0= ruleRelation ) ) ( ( ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) ) ) ( (lv_number_2_0= RULE_INT ) ) )? ( (lv_concept_3_0= ruleConceptExpression ) ) ) | ( ( (lv_relation_4_0= ruleDatatypeRelation ) ) ( (lv_data_5_0= ruleDataStatement ) ) ) ) )
            // InternalArchcnl.g:1035:2: ( ( ( (lv_relation_0_0= ruleRelation ) ) ( ( ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) ) ) ( (lv_number_2_0= RULE_INT ) ) )? ( (lv_concept_3_0= ruleConceptExpression ) ) ) | ( ( (lv_relation_4_0= ruleDatatypeRelation ) ) ( (lv_data_5_0= ruleDataStatement ) ) ) )
            {
            // InternalArchcnl.g:1035:2: ( ( ( (lv_relation_0_0= ruleRelation ) ) ( ( ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) ) ) ( (lv_number_2_0= RULE_INT ) ) )? ( (lv_concept_3_0= ruleConceptExpression ) ) ) | ( ( (lv_relation_4_0= ruleDatatypeRelation ) ) ( (lv_data_5_0= ruleDataStatement ) ) ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_RELATION_NAME) ) {
                int LA10_1 = input.LA(2);

                if ( (LA10_1==34) ) {
                    int LA10_2 = input.LA(3);

                    if ( (LA10_2==RULE_NAME||(LA10_2>=29 && LA10_2<=31)||(LA10_2>=35 && LA10_2<=36)) ) {
                        alt10=1;
                    }
                    else if ( (LA10_2==RULE_INT||LA10_2==RULE_STRING) ) {
                        alt10=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA10_1==RULE_NAME||(LA10_1>=29 && LA10_1<=31)||(LA10_1>=35 && LA10_1<=36)) ) {
                    alt10=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalArchcnl.g:1036:3: ( ( (lv_relation_0_0= ruleRelation ) ) ( ( ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) ) ) ( (lv_number_2_0= RULE_INT ) ) )? ( (lv_concept_3_0= ruleConceptExpression ) ) )
                    {
                    // InternalArchcnl.g:1036:3: ( ( (lv_relation_0_0= ruleRelation ) ) ( ( ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) ) ) ( (lv_number_2_0= RULE_INT ) ) )? ( (lv_concept_3_0= ruleConceptExpression ) ) )
                    // InternalArchcnl.g:1037:4: ( (lv_relation_0_0= ruleRelation ) ) ( ( ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) ) ) ( (lv_number_2_0= RULE_INT ) ) )? ( (lv_concept_3_0= ruleConceptExpression ) )
                    {
                    // InternalArchcnl.g:1037:4: ( (lv_relation_0_0= ruleRelation ) )
                    // InternalArchcnl.g:1038:5: (lv_relation_0_0= ruleRelation )
                    {
                    // InternalArchcnl.g:1038:5: (lv_relation_0_0= ruleRelation )
                    // InternalArchcnl.g:1039:6: lv_relation_0_0= ruleRelation
                    {

                    						newCompositeNode(grammarAccess.getObjectConceptExpressionAccess().getRelationRelationParserRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_relation_0_0=ruleRelation();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getObjectConceptExpressionRule());
                    						}
                    						set(
                    							current,
                    							"relation",
                    							lv_relation_0_0,
                    							"org.architecture.cnl.Archcnl.Relation");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalArchcnl.g:1056:4: ( ( ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) ) ) ( (lv_number_2_0= RULE_INT ) ) )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( ((LA9_0>=29 && LA9_0<=31)) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // InternalArchcnl.g:1057:5: ( ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) ) ) ( (lv_number_2_0= RULE_INT ) )
                            {
                            // InternalArchcnl.g:1057:5: ( ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) ) )
                            // InternalArchcnl.g:1058:6: ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) )
                            {
                            // InternalArchcnl.g:1058:6: ( (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' ) )
                            // InternalArchcnl.g:1059:7: (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' )
                            {
                            // InternalArchcnl.g:1059:7: (lv_cardinality_1_1= 'at-most' | lv_cardinality_1_2= 'at-least' | lv_cardinality_1_3= 'exactly' )
                            int alt8=3;
                            switch ( input.LA(1) ) {
                            case 29:
                                {
                                alt8=1;
                                }
                                break;
                            case 30:
                                {
                                alt8=2;
                                }
                                break;
                            case 31:
                                {
                                alt8=3;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 8, 0, input);

                                throw nvae;
                            }

                            switch (alt8) {
                                case 1 :
                                    // InternalArchcnl.g:1060:8: lv_cardinality_1_1= 'at-most'
                                    {
                                    lv_cardinality_1_1=(Token)match(input,29,FOLLOW_18); 

                                    								newLeafNode(lv_cardinality_1_1, grammarAccess.getObjectConceptExpressionAccess().getCardinalityAtMostKeyword_0_1_0_0_0());
                                    							

                                    								if (current==null) {
                                    									current = createModelElement(grammarAccess.getObjectConceptExpressionRule());
                                    								}
                                    								setWithLastConsumed(current, "cardinality", lv_cardinality_1_1, null);
                                    							

                                    }
                                    break;
                                case 2 :
                                    // InternalArchcnl.g:1071:8: lv_cardinality_1_2= 'at-least'
                                    {
                                    lv_cardinality_1_2=(Token)match(input,30,FOLLOW_18); 

                                    								newLeafNode(lv_cardinality_1_2, grammarAccess.getObjectConceptExpressionAccess().getCardinalityAtLeastKeyword_0_1_0_0_1());
                                    							

                                    								if (current==null) {
                                    									current = createModelElement(grammarAccess.getObjectConceptExpressionRule());
                                    								}
                                    								setWithLastConsumed(current, "cardinality", lv_cardinality_1_2, null);
                                    							

                                    }
                                    break;
                                case 3 :
                                    // InternalArchcnl.g:1082:8: lv_cardinality_1_3= 'exactly'
                                    {
                                    lv_cardinality_1_3=(Token)match(input,31,FOLLOW_18); 

                                    								newLeafNode(lv_cardinality_1_3, grammarAccess.getObjectConceptExpressionAccess().getCardinalityExactlyKeyword_0_1_0_0_2());
                                    							

                                    								if (current==null) {
                                    									current = createModelElement(grammarAccess.getObjectConceptExpressionRule());
                                    								}
                                    								setWithLastConsumed(current, "cardinality", lv_cardinality_1_3, null);
                                    							

                                    }
                                    break;

                            }


                            }


                            }

                            // InternalArchcnl.g:1095:5: ( (lv_number_2_0= RULE_INT ) )
                            // InternalArchcnl.g:1096:6: (lv_number_2_0= RULE_INT )
                            {
                            // InternalArchcnl.g:1096:6: (lv_number_2_0= RULE_INT )
                            // InternalArchcnl.g:1097:7: lv_number_2_0= RULE_INT
                            {
                            lv_number_2_0=(Token)match(input,RULE_INT,FOLLOW_4); 

                            							newLeafNode(lv_number_2_0, grammarAccess.getObjectConceptExpressionAccess().getNumberINTTerminalRuleCall_0_1_1_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getObjectConceptExpressionRule());
                            							}
                            							setWithLastConsumed(
                            								current,
                            								"number",
                            								lv_number_2_0,
                            								"org.eclipse.xtext.common.Terminals.INT");
                            						

                            }


                            }


                            }
                            break;

                    }

                    // InternalArchcnl.g:1114:4: ( (lv_concept_3_0= ruleConceptExpression ) )
                    // InternalArchcnl.g:1115:5: (lv_concept_3_0= ruleConceptExpression )
                    {
                    // InternalArchcnl.g:1115:5: (lv_concept_3_0= ruleConceptExpression )
                    // InternalArchcnl.g:1116:6: lv_concept_3_0= ruleConceptExpression
                    {

                    						newCompositeNode(grammarAccess.getObjectConceptExpressionAccess().getConceptConceptExpressionParserRuleCall_0_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_concept_3_0=ruleConceptExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getObjectConceptExpressionRule());
                    						}
                    						set(
                    							current,
                    							"concept",
                    							lv_concept_3_0,
                    							"org.architecture.cnl.Archcnl.ConceptExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1135:3: ( ( (lv_relation_4_0= ruleDatatypeRelation ) ) ( (lv_data_5_0= ruleDataStatement ) ) )
                    {
                    // InternalArchcnl.g:1135:3: ( ( (lv_relation_4_0= ruleDatatypeRelation ) ) ( (lv_data_5_0= ruleDataStatement ) ) )
                    // InternalArchcnl.g:1136:4: ( (lv_relation_4_0= ruleDatatypeRelation ) ) ( (lv_data_5_0= ruleDataStatement ) )
                    {
                    // InternalArchcnl.g:1136:4: ( (lv_relation_4_0= ruleDatatypeRelation ) )
                    // InternalArchcnl.g:1137:5: (lv_relation_4_0= ruleDatatypeRelation )
                    {
                    // InternalArchcnl.g:1137:5: (lv_relation_4_0= ruleDatatypeRelation )
                    // InternalArchcnl.g:1138:6: lv_relation_4_0= ruleDatatypeRelation
                    {

                    						newCompositeNode(grammarAccess.getObjectConceptExpressionAccess().getRelationDatatypeRelationParserRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_19);
                    lv_relation_4_0=ruleDatatypeRelation();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getObjectConceptExpressionRule());
                    						}
                    						set(
                    							current,
                    							"relation",
                    							lv_relation_4_0,
                    							"org.architecture.cnl.Archcnl.DatatypeRelation");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalArchcnl.g:1155:4: ( (lv_data_5_0= ruleDataStatement ) )
                    // InternalArchcnl.g:1156:5: (lv_data_5_0= ruleDataStatement )
                    {
                    // InternalArchcnl.g:1156:5: (lv_data_5_0= ruleDataStatement )
                    // InternalArchcnl.g:1157:6: lv_data_5_0= ruleDataStatement
                    {

                    						newCompositeNode(grammarAccess.getObjectConceptExpressionAccess().getDataDataStatementParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_data_5_0=ruleDataStatement();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getObjectConceptExpressionRule());
                    						}
                    						set(
                    							current,
                    							"data",
                    							lv_data_5_0,
                    							"org.architecture.cnl.Archcnl.DataStatement");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleObjectConceptExpression"


    // $ANTLR start "entryRuleAndObjectConceptExpression"
    // InternalArchcnl.g:1179:1: entryRuleAndObjectConceptExpression returns [EObject current=null] : iv_ruleAndObjectConceptExpression= ruleAndObjectConceptExpression EOF ;
    public final EObject entryRuleAndObjectConceptExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndObjectConceptExpression = null;


        try {
            // InternalArchcnl.g:1179:67: (iv_ruleAndObjectConceptExpression= ruleAndObjectConceptExpression EOF )
            // InternalArchcnl.g:1180:2: iv_ruleAndObjectConceptExpression= ruleAndObjectConceptExpression EOF
            {
             newCompositeNode(grammarAccess.getAndObjectConceptExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAndObjectConceptExpression=ruleAndObjectConceptExpression();

            state._fsp--;

             current =iv_ruleAndObjectConceptExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAndObjectConceptExpression"


    // $ANTLR start "ruleAndObjectConceptExpression"
    // InternalArchcnl.g:1186:1: ruleAndObjectConceptExpression returns [EObject current=null] : (otherlv_0= 'and' ( (lv_expression_1_0= ruleObjectConceptExpression ) ) ) ;
    public final EObject ruleAndObjectConceptExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_expression_1_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1192:2: ( (otherlv_0= 'and' ( (lv_expression_1_0= ruleObjectConceptExpression ) ) ) )
            // InternalArchcnl.g:1193:2: (otherlv_0= 'and' ( (lv_expression_1_0= ruleObjectConceptExpression ) ) )
            {
            // InternalArchcnl.g:1193:2: (otherlv_0= 'and' ( (lv_expression_1_0= ruleObjectConceptExpression ) ) )
            // InternalArchcnl.g:1194:3: otherlv_0= 'and' ( (lv_expression_1_0= ruleObjectConceptExpression ) )
            {
            otherlv_0=(Token)match(input,32,FOLLOW_7); 

            			newLeafNode(otherlv_0, grammarAccess.getAndObjectConceptExpressionAccess().getAndKeyword_0());
            		
            // InternalArchcnl.g:1198:3: ( (lv_expression_1_0= ruleObjectConceptExpression ) )
            // InternalArchcnl.g:1199:4: (lv_expression_1_0= ruleObjectConceptExpression )
            {
            // InternalArchcnl.g:1199:4: (lv_expression_1_0= ruleObjectConceptExpression )
            // InternalArchcnl.g:1200:5: lv_expression_1_0= ruleObjectConceptExpression
            {

            					newCompositeNode(grammarAccess.getAndObjectConceptExpressionAccess().getExpressionObjectConceptExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_expression_1_0=ruleObjectConceptExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAndObjectConceptExpressionRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_1_0,
            						"org.architecture.cnl.Archcnl.ObjectConceptExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAndObjectConceptExpression"


    // $ANTLR start "entryRuleOrObjectConceptExpression"
    // InternalArchcnl.g:1221:1: entryRuleOrObjectConceptExpression returns [EObject current=null] : iv_ruleOrObjectConceptExpression= ruleOrObjectConceptExpression EOF ;
    public final EObject entryRuleOrObjectConceptExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrObjectConceptExpression = null;


        try {
            // InternalArchcnl.g:1221:66: (iv_ruleOrObjectConceptExpression= ruleOrObjectConceptExpression EOF )
            // InternalArchcnl.g:1222:2: iv_ruleOrObjectConceptExpression= ruleOrObjectConceptExpression EOF
            {
             newCompositeNode(grammarAccess.getOrObjectConceptExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOrObjectConceptExpression=ruleOrObjectConceptExpression();

            state._fsp--;

             current =iv_ruleOrObjectConceptExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOrObjectConceptExpression"


    // $ANTLR start "ruleOrObjectConceptExpression"
    // InternalArchcnl.g:1228:1: ruleOrObjectConceptExpression returns [EObject current=null] : (otherlv_0= 'or' ( (lv_expression_1_0= ruleObjectConceptExpression ) ) ) ;
    public final EObject ruleOrObjectConceptExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_expression_1_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1234:2: ( (otherlv_0= 'or' ( (lv_expression_1_0= ruleObjectConceptExpression ) ) ) )
            // InternalArchcnl.g:1235:2: (otherlv_0= 'or' ( (lv_expression_1_0= ruleObjectConceptExpression ) ) )
            {
            // InternalArchcnl.g:1235:2: (otherlv_0= 'or' ( (lv_expression_1_0= ruleObjectConceptExpression ) ) )
            // InternalArchcnl.g:1236:3: otherlv_0= 'or' ( (lv_expression_1_0= ruleObjectConceptExpression ) )
            {
            otherlv_0=(Token)match(input,33,FOLLOW_7); 

            			newLeafNode(otherlv_0, grammarAccess.getOrObjectConceptExpressionAccess().getOrKeyword_0());
            		
            // InternalArchcnl.g:1240:3: ( (lv_expression_1_0= ruleObjectConceptExpression ) )
            // InternalArchcnl.g:1241:4: (lv_expression_1_0= ruleObjectConceptExpression )
            {
            // InternalArchcnl.g:1241:4: (lv_expression_1_0= ruleObjectConceptExpression )
            // InternalArchcnl.g:1242:5: lv_expression_1_0= ruleObjectConceptExpression
            {

            					newCompositeNode(grammarAccess.getOrObjectConceptExpressionAccess().getExpressionObjectConceptExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_expression_1_0=ruleObjectConceptExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOrObjectConceptExpressionRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_1_0,
            						"org.architecture.cnl.Archcnl.ObjectConceptExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrObjectConceptExpression"


    // $ANTLR start "entryRuleRelation"
    // InternalArchcnl.g:1263:1: entryRuleRelation returns [EObject current=null] : iv_ruleRelation= ruleRelation EOF ;
    public final EObject entryRuleRelation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRelation = null;


        try {
            // InternalArchcnl.g:1263:49: (iv_ruleRelation= ruleRelation EOF )
            // InternalArchcnl.g:1264:2: iv_ruleRelation= ruleRelation EOF
            {
             newCompositeNode(grammarAccess.getRelationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRelation=ruleRelation();

            state._fsp--;

             current =iv_ruleRelation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRelation"


    // $ANTLR start "ruleRelation"
    // InternalArchcnl.g:1270:1: ruleRelation returns [EObject current=null] : (this_ObjectRelation_0= ruleObjectRelation | this_DatatypeRelation_1= ruleDatatypeRelation ) ;
    public final EObject ruleRelation() throws RecognitionException {
        EObject current = null;

        EObject this_ObjectRelation_0 = null;

        EObject this_DatatypeRelation_1 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1276:2: ( (this_ObjectRelation_0= ruleObjectRelation | this_DatatypeRelation_1= ruleDatatypeRelation ) )
            // InternalArchcnl.g:1277:2: (this_ObjectRelation_0= ruleObjectRelation | this_DatatypeRelation_1= ruleDatatypeRelation )
            {
            // InternalArchcnl.g:1277:2: (this_ObjectRelation_0= ruleObjectRelation | this_DatatypeRelation_1= ruleDatatypeRelation )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_RELATION_NAME) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==34) ) {
                    alt11=2;
                }
                else if ( (LA11_1==EOF||LA11_1==RULE_INT||(LA11_1>=RULE_STRING && LA11_1<=RULE_NAME)||LA11_1==18||LA11_1==25||(LA11_1>=29 && LA11_1<=31)||(LA11_1>=35 && LA11_1<=36)) ) {
                    alt11=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalArchcnl.g:1278:3: this_ObjectRelation_0= ruleObjectRelation
                    {

                    			newCompositeNode(grammarAccess.getRelationAccess().getObjectRelationParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_ObjectRelation_0=ruleObjectRelation();

                    state._fsp--;


                    			current = this_ObjectRelation_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1287:3: this_DatatypeRelation_1= ruleDatatypeRelation
                    {

                    			newCompositeNode(grammarAccess.getRelationAccess().getDatatypeRelationParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_DatatypeRelation_1=ruleDatatypeRelation();

                    state._fsp--;


                    			current = this_DatatypeRelation_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRelation"


    // $ANTLR start "entryRuleDatatypeRelation"
    // InternalArchcnl.g:1299:1: entryRuleDatatypeRelation returns [EObject current=null] : iv_ruleDatatypeRelation= ruleDatatypeRelation EOF ;
    public final EObject entryRuleDatatypeRelation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDatatypeRelation = null;


        try {
            // InternalArchcnl.g:1299:57: (iv_ruleDatatypeRelation= ruleDatatypeRelation EOF )
            // InternalArchcnl.g:1300:2: iv_ruleDatatypeRelation= ruleDatatypeRelation EOF
            {
             newCompositeNode(grammarAccess.getDatatypeRelationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDatatypeRelation=ruleDatatypeRelation();

            state._fsp--;

             current =iv_ruleDatatypeRelation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDatatypeRelation"


    // $ANTLR start "ruleDatatypeRelation"
    // InternalArchcnl.g:1306:1: ruleDatatypeRelation returns [EObject current=null] : ( ( (lv_relationName_0_0= RULE_RELATION_NAME ) ) otherlv_1= 'equal-to' ) ;
    public final EObject ruleDatatypeRelation() throws RecognitionException {
        EObject current = null;

        Token lv_relationName_0_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalArchcnl.g:1312:2: ( ( ( (lv_relationName_0_0= RULE_RELATION_NAME ) ) otherlv_1= 'equal-to' ) )
            // InternalArchcnl.g:1313:2: ( ( (lv_relationName_0_0= RULE_RELATION_NAME ) ) otherlv_1= 'equal-to' )
            {
            // InternalArchcnl.g:1313:2: ( ( (lv_relationName_0_0= RULE_RELATION_NAME ) ) otherlv_1= 'equal-to' )
            // InternalArchcnl.g:1314:3: ( (lv_relationName_0_0= RULE_RELATION_NAME ) ) otherlv_1= 'equal-to'
            {
            // InternalArchcnl.g:1314:3: ( (lv_relationName_0_0= RULE_RELATION_NAME ) )
            // InternalArchcnl.g:1315:4: (lv_relationName_0_0= RULE_RELATION_NAME )
            {
            // InternalArchcnl.g:1315:4: (lv_relationName_0_0= RULE_RELATION_NAME )
            // InternalArchcnl.g:1316:5: lv_relationName_0_0= RULE_RELATION_NAME
            {
            lv_relationName_0_0=(Token)match(input,RULE_RELATION_NAME,FOLLOW_20); 

            					newLeafNode(lv_relationName_0_0, grammarAccess.getDatatypeRelationAccess().getRelationNameRELATION_NAMETerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDatatypeRelationRule());
            					}
            					setWithLastConsumed(
            						current,
            						"relationName",
            						lv_relationName_0_0,
            						"org.architecture.cnl.Archcnl.RELATION_NAME");
            				

            }


            }

            otherlv_1=(Token)match(input,34,FOLLOW_2); 

            			newLeafNode(otherlv_1, grammarAccess.getDatatypeRelationAccess().getEqualToKeyword_1());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDatatypeRelation"


    // $ANTLR start "entryRuleObjectRelation"
    // InternalArchcnl.g:1340:1: entryRuleObjectRelation returns [EObject current=null] : iv_ruleObjectRelation= ruleObjectRelation EOF ;
    public final EObject entryRuleObjectRelation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleObjectRelation = null;


        try {
            // InternalArchcnl.g:1340:55: (iv_ruleObjectRelation= ruleObjectRelation EOF )
            // InternalArchcnl.g:1341:2: iv_ruleObjectRelation= ruleObjectRelation EOF
            {
             newCompositeNode(grammarAccess.getObjectRelationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleObjectRelation=ruleObjectRelation();

            state._fsp--;

             current =iv_ruleObjectRelation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleObjectRelation"


    // $ANTLR start "ruleObjectRelation"
    // InternalArchcnl.g:1347:1: ruleObjectRelation returns [EObject current=null] : ( (lv_relationName_0_0= RULE_RELATION_NAME ) ) ;
    public final EObject ruleObjectRelation() throws RecognitionException {
        EObject current = null;

        Token lv_relationName_0_0=null;


        	enterRule();

        try {
            // InternalArchcnl.g:1353:2: ( ( (lv_relationName_0_0= RULE_RELATION_NAME ) ) )
            // InternalArchcnl.g:1354:2: ( (lv_relationName_0_0= RULE_RELATION_NAME ) )
            {
            // InternalArchcnl.g:1354:2: ( (lv_relationName_0_0= RULE_RELATION_NAME ) )
            // InternalArchcnl.g:1355:3: (lv_relationName_0_0= RULE_RELATION_NAME )
            {
            // InternalArchcnl.g:1355:3: (lv_relationName_0_0= RULE_RELATION_NAME )
            // InternalArchcnl.g:1356:4: lv_relationName_0_0= RULE_RELATION_NAME
            {
            lv_relationName_0_0=(Token)match(input,RULE_RELATION_NAME,FOLLOW_2); 

            				newLeafNode(lv_relationName_0_0, grammarAccess.getObjectRelationAccess().getRelationNameRELATION_NAMETerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getObjectRelationRule());
            				}
            				setWithLastConsumed(
            					current,
            					"relationName",
            					lv_relationName_0_0,
            					"org.architecture.cnl.Archcnl.RELATION_NAME");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleObjectRelation"


    // $ANTLR start "entryRuleConceptExpression"
    // InternalArchcnl.g:1375:1: entryRuleConceptExpression returns [EObject current=null] : iv_ruleConceptExpression= ruleConceptExpression EOF ;
    public final EObject entryRuleConceptExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConceptExpression = null;


        try {
            // InternalArchcnl.g:1375:58: (iv_ruleConceptExpression= ruleConceptExpression EOF )
            // InternalArchcnl.g:1376:2: iv_ruleConceptExpression= ruleConceptExpression EOF
            {
             newCompositeNode(grammarAccess.getConceptExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConceptExpression=ruleConceptExpression();

            state._fsp--;

             current =iv_ruleConceptExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConceptExpression"


    // $ANTLR start "ruleConceptExpression"
    // InternalArchcnl.g:1382:1: ruleConceptExpression returns [EObject current=null] : ( (otherlv_0= 'a' | otherlv_1= 'an' )? ( (lv_concept_2_0= ruleConcept ) ) ( (lv_that_3_0= ruleThatExpression ) )* ) ;
    public final EObject ruleConceptExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_concept_2_0 = null;

        EObject lv_that_3_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1388:2: ( ( (otherlv_0= 'a' | otherlv_1= 'an' )? ( (lv_concept_2_0= ruleConcept ) ) ( (lv_that_3_0= ruleThatExpression ) )* ) )
            // InternalArchcnl.g:1389:2: ( (otherlv_0= 'a' | otherlv_1= 'an' )? ( (lv_concept_2_0= ruleConcept ) ) ( (lv_that_3_0= ruleThatExpression ) )* )
            {
            // InternalArchcnl.g:1389:2: ( (otherlv_0= 'a' | otherlv_1= 'an' )? ( (lv_concept_2_0= ruleConcept ) ) ( (lv_that_3_0= ruleThatExpression ) )* )
            // InternalArchcnl.g:1390:3: (otherlv_0= 'a' | otherlv_1= 'an' )? ( (lv_concept_2_0= ruleConcept ) ) ( (lv_that_3_0= ruleThatExpression ) )*
            {
            // InternalArchcnl.g:1390:3: (otherlv_0= 'a' | otherlv_1= 'an' )?
            int alt12=3;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==35) ) {
                alt12=1;
            }
            else if ( (LA12_0==36) ) {
                alt12=2;
            }
            switch (alt12) {
                case 1 :
                    // InternalArchcnl.g:1391:4: otherlv_0= 'a'
                    {
                    otherlv_0=(Token)match(input,35,FOLLOW_4); 

                    				newLeafNode(otherlv_0, grammarAccess.getConceptExpressionAccess().getAKeyword_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1396:4: otherlv_1= 'an'
                    {
                    otherlv_1=(Token)match(input,36,FOLLOW_4); 

                    				newLeafNode(otherlv_1, grammarAccess.getConceptExpressionAccess().getAnKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalArchcnl.g:1401:3: ( (lv_concept_2_0= ruleConcept ) )
            // InternalArchcnl.g:1402:4: (lv_concept_2_0= ruleConcept )
            {
            // InternalArchcnl.g:1402:4: (lv_concept_2_0= ruleConcept )
            // InternalArchcnl.g:1403:5: lv_concept_2_0= ruleConcept
            {

            					newCompositeNode(grammarAccess.getConceptExpressionAccess().getConceptConceptParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_21);
            lv_concept_2_0=ruleConcept();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConceptExpressionRule());
            					}
            					set(
            						current,
            						"concept",
            						lv_concept_2_0,
            						"org.architecture.cnl.Archcnl.Concept");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalArchcnl.g:1420:3: ( (lv_that_3_0= ruleThatExpression ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==37) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalArchcnl.g:1421:4: (lv_that_3_0= ruleThatExpression )
            	    {
            	    // InternalArchcnl.g:1421:4: (lv_that_3_0= ruleThatExpression )
            	    // InternalArchcnl.g:1422:5: lv_that_3_0= ruleThatExpression
            	    {

            	    					newCompositeNode(grammarAccess.getConceptExpressionAccess().getThatThatExpressionParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_21);
            	    lv_that_3_0=ruleThatExpression();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getConceptExpressionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"that",
            	    						lv_that_3_0,
            	    						"org.architecture.cnl.Archcnl.ThatExpression");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConceptExpression"


    // $ANTLR start "entryRuleThatExpression"
    // InternalArchcnl.g:1443:1: entryRuleThatExpression returns [EObject current=null] : iv_ruleThatExpression= ruleThatExpression EOF ;
    public final EObject entryRuleThatExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThatExpression = null;


        try {
            // InternalArchcnl.g:1443:55: (iv_ruleThatExpression= ruleThatExpression EOF )
            // InternalArchcnl.g:1444:2: iv_ruleThatExpression= ruleThatExpression EOF
            {
             newCompositeNode(grammarAccess.getThatExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleThatExpression=ruleThatExpression();

            state._fsp--;

             current =iv_ruleThatExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleThatExpression"


    // $ANTLR start "ruleThatExpression"
    // InternalArchcnl.g:1450:1: ruleThatExpression returns [EObject current=null] : (otherlv_0= 'that' otherlv_1= '(' ( (lv_list_2_0= ruleStatementList ) )+ otherlv_3= ')' ) ;
    public final EObject ruleThatExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_list_2_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1456:2: ( (otherlv_0= 'that' otherlv_1= '(' ( (lv_list_2_0= ruleStatementList ) )+ otherlv_3= ')' ) )
            // InternalArchcnl.g:1457:2: (otherlv_0= 'that' otherlv_1= '(' ( (lv_list_2_0= ruleStatementList ) )+ otherlv_3= ')' )
            {
            // InternalArchcnl.g:1457:2: (otherlv_0= 'that' otherlv_1= '(' ( (lv_list_2_0= ruleStatementList ) )+ otherlv_3= ')' )
            // InternalArchcnl.g:1458:3: otherlv_0= 'that' otherlv_1= '(' ( (lv_list_2_0= ruleStatementList ) )+ otherlv_3= ')'
            {
            otherlv_0=(Token)match(input,37,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getThatExpressionAccess().getThatKeyword_0());
            		
            otherlv_1=(Token)match(input,38,FOLLOW_23); 

            			newLeafNode(otherlv_1, grammarAccess.getThatExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalArchcnl.g:1466:3: ( (lv_list_2_0= ruleStatementList ) )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==RULE_RELATION_NAME||LA14_0==32) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalArchcnl.g:1467:4: (lv_list_2_0= ruleStatementList )
            	    {
            	    // InternalArchcnl.g:1467:4: (lv_list_2_0= ruleStatementList )
            	    // InternalArchcnl.g:1468:5: lv_list_2_0= ruleStatementList
            	    {

            	    					newCompositeNode(grammarAccess.getThatExpressionAccess().getListStatementListParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_24);
            	    lv_list_2_0=ruleStatementList();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getThatExpressionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"list",
            	    						lv_list_2_0,
            	    						"org.architecture.cnl.Archcnl.StatementList");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);

            otherlv_3=(Token)match(input,39,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getThatExpressionAccess().getRightParenthesisKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleThatExpression"


    // $ANTLR start "entryRuleStatementList"
    // InternalArchcnl.g:1493:1: entryRuleStatementList returns [EObject current=null] : iv_ruleStatementList= ruleStatementList EOF ;
    public final EObject entryRuleStatementList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementList = null;


        try {
            // InternalArchcnl.g:1493:54: (iv_ruleStatementList= ruleStatementList EOF )
            // InternalArchcnl.g:1494:2: iv_ruleStatementList= ruleStatementList EOF
            {
             newCompositeNode(grammarAccess.getStatementListRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStatementList=ruleStatementList();

            state._fsp--;

             current =iv_ruleStatementList; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatementList"


    // $ANTLR start "ruleStatementList"
    // InternalArchcnl.g:1500:1: ruleStatementList returns [EObject current=null] : ( (otherlv_0= 'and' )? ( ( (lv_relation_1_0= ruleRelation ) ) ( ( (lv_expression_2_0= ruleConceptExpression ) ) | ( (lv_expression_3_0= ruleDataStatement ) ) | ( (lv_expression_4_0= ruleVariableStatement ) ) ) ) ) ;
    public final EObject ruleStatementList() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_relation_1_0 = null;

        EObject lv_expression_2_0 = null;

        EObject lv_expression_3_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1506:2: ( ( (otherlv_0= 'and' )? ( ( (lv_relation_1_0= ruleRelation ) ) ( ( (lv_expression_2_0= ruleConceptExpression ) ) | ( (lv_expression_3_0= ruleDataStatement ) ) | ( (lv_expression_4_0= ruleVariableStatement ) ) ) ) ) )
            // InternalArchcnl.g:1507:2: ( (otherlv_0= 'and' )? ( ( (lv_relation_1_0= ruleRelation ) ) ( ( (lv_expression_2_0= ruleConceptExpression ) ) | ( (lv_expression_3_0= ruleDataStatement ) ) | ( (lv_expression_4_0= ruleVariableStatement ) ) ) ) )
            {
            // InternalArchcnl.g:1507:2: ( (otherlv_0= 'and' )? ( ( (lv_relation_1_0= ruleRelation ) ) ( ( (lv_expression_2_0= ruleConceptExpression ) ) | ( (lv_expression_3_0= ruleDataStatement ) ) | ( (lv_expression_4_0= ruleVariableStatement ) ) ) ) )
            // InternalArchcnl.g:1508:3: (otherlv_0= 'and' )? ( ( (lv_relation_1_0= ruleRelation ) ) ( ( (lv_expression_2_0= ruleConceptExpression ) ) | ( (lv_expression_3_0= ruleDataStatement ) ) | ( (lv_expression_4_0= ruleVariableStatement ) ) ) )
            {
            // InternalArchcnl.g:1508:3: (otherlv_0= 'and' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==32) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalArchcnl.g:1509:4: otherlv_0= 'and'
                    {
                    otherlv_0=(Token)match(input,32,FOLLOW_7); 

                    				newLeafNode(otherlv_0, grammarAccess.getStatementListAccess().getAndKeyword_0());
                    			

                    }
                    break;

            }

            // InternalArchcnl.g:1514:3: ( ( (lv_relation_1_0= ruleRelation ) ) ( ( (lv_expression_2_0= ruleConceptExpression ) ) | ( (lv_expression_3_0= ruleDataStatement ) ) | ( (lv_expression_4_0= ruleVariableStatement ) ) ) )
            // InternalArchcnl.g:1515:4: ( (lv_relation_1_0= ruleRelation ) ) ( ( (lv_expression_2_0= ruleConceptExpression ) ) | ( (lv_expression_3_0= ruleDataStatement ) ) | ( (lv_expression_4_0= ruleVariableStatement ) ) )
            {
            // InternalArchcnl.g:1515:4: ( (lv_relation_1_0= ruleRelation ) )
            // InternalArchcnl.g:1516:5: (lv_relation_1_0= ruleRelation )
            {
            // InternalArchcnl.g:1516:5: (lv_relation_1_0= ruleRelation )
            // InternalArchcnl.g:1517:6: lv_relation_1_0= ruleRelation
            {

            						newCompositeNode(grammarAccess.getStatementListAccess().getRelationRelationParserRuleCall_1_0_0());
            					
            pushFollow(FOLLOW_25);
            lv_relation_1_0=ruleRelation();

            state._fsp--;


            						if (current==null) {
            							current = createModelElementForParent(grammarAccess.getStatementListRule());
            						}
            						set(
            							current,
            							"relation",
            							lv_relation_1_0,
            							"org.architecture.cnl.Archcnl.Relation");
            						afterParserOrEnumRuleCall();
            					

            }


            }

            // InternalArchcnl.g:1534:4: ( ( (lv_expression_2_0= ruleConceptExpression ) ) | ( (lv_expression_3_0= ruleDataStatement ) ) | ( (lv_expression_4_0= ruleVariableStatement ) ) )
            int alt16=3;
            switch ( input.LA(1) ) {
            case 35:
            case 36:
                {
                alt16=1;
                }
                break;
            case RULE_NAME:
                {
                int LA16_2 = input.LA(2);

                if ( (LA16_2==EOF||LA16_2==RULE_RELATION_NAME||LA16_2==32||LA16_2==37||LA16_2==39) ) {
                    alt16=1;
                }
                else if ( (LA16_2==RULE_VARIABLE_NAME) ) {
                    alt16=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_INT:
            case RULE_STRING:
                {
                alt16=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // InternalArchcnl.g:1535:5: ( (lv_expression_2_0= ruleConceptExpression ) )
                    {
                    // InternalArchcnl.g:1535:5: ( (lv_expression_2_0= ruleConceptExpression ) )
                    // InternalArchcnl.g:1536:6: (lv_expression_2_0= ruleConceptExpression )
                    {
                    // InternalArchcnl.g:1536:6: (lv_expression_2_0= ruleConceptExpression )
                    // InternalArchcnl.g:1537:7: lv_expression_2_0= ruleConceptExpression
                    {

                    							newCompositeNode(grammarAccess.getStatementListAccess().getExpressionConceptExpressionParserRuleCall_1_1_0_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_expression_2_0=ruleConceptExpression();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getStatementListRule());
                    							}
                    							set(
                    								current,
                    								"expression",
                    								lv_expression_2_0,
                    								"org.architecture.cnl.Archcnl.ConceptExpression");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1555:5: ( (lv_expression_3_0= ruleDataStatement ) )
                    {
                    // InternalArchcnl.g:1555:5: ( (lv_expression_3_0= ruleDataStatement ) )
                    // InternalArchcnl.g:1556:6: (lv_expression_3_0= ruleDataStatement )
                    {
                    // InternalArchcnl.g:1556:6: (lv_expression_3_0= ruleDataStatement )
                    // InternalArchcnl.g:1557:7: lv_expression_3_0= ruleDataStatement
                    {

                    							newCompositeNode(grammarAccess.getStatementListAccess().getExpressionDataStatementParserRuleCall_1_1_1_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_expression_3_0=ruleDataStatement();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getStatementListRule());
                    							}
                    							set(
                    								current,
                    								"expression",
                    								lv_expression_3_0,
                    								"org.architecture.cnl.Archcnl.DataStatement");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalArchcnl.g:1575:5: ( (lv_expression_4_0= ruleVariableStatement ) )
                    {
                    // InternalArchcnl.g:1575:5: ( (lv_expression_4_0= ruleVariableStatement ) )
                    // InternalArchcnl.g:1576:6: (lv_expression_4_0= ruleVariableStatement )
                    {
                    // InternalArchcnl.g:1576:6: (lv_expression_4_0= ruleVariableStatement )
                    // InternalArchcnl.g:1577:7: lv_expression_4_0= ruleVariableStatement
                    {

                    							newCompositeNode(grammarAccess.getStatementListAccess().getExpressionVariableStatementParserRuleCall_1_1_2_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_expression_4_0=ruleVariableStatement();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getStatementListRule());
                    							}
                    							set(
                    								current,
                    								"expression",
                    								lv_expression_4_0,
                    								"org.architecture.cnl.Archcnl.VariableStatement");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }
                    break;

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatementList"


    // $ANTLR start "entryRuleVariableStatement"
    // InternalArchcnl.g:1600:1: entryRuleVariableStatement returns [EObject current=null] : iv_ruleVariableStatement= ruleVariableStatement EOF ;
    public final EObject entryRuleVariableStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableStatement = null;


        try {
            // InternalArchcnl.g:1600:58: (iv_ruleVariableStatement= ruleVariableStatement EOF )
            // InternalArchcnl.g:1601:2: iv_ruleVariableStatement= ruleVariableStatement EOF
            {
             newCompositeNode(grammarAccess.getVariableStatementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariableStatement=ruleVariableStatement();

            state._fsp--;

             current =iv_ruleVariableStatement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariableStatement"


    // $ANTLR start "ruleVariableStatement"
    // InternalArchcnl.g:1607:1: ruleVariableStatement returns [EObject current=null] : ( ( (lv_concept_0_0= ruleConcept ) ) ( (lv_variable_1_0= ruleVariable ) ) ) ;
    public final EObject ruleVariableStatement() throws RecognitionException {
        EObject current = null;

        EObject lv_concept_0_0 = null;

        EObject lv_variable_1_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1613:2: ( ( ( (lv_concept_0_0= ruleConcept ) ) ( (lv_variable_1_0= ruleVariable ) ) ) )
            // InternalArchcnl.g:1614:2: ( ( (lv_concept_0_0= ruleConcept ) ) ( (lv_variable_1_0= ruleVariable ) ) )
            {
            // InternalArchcnl.g:1614:2: ( ( (lv_concept_0_0= ruleConcept ) ) ( (lv_variable_1_0= ruleVariable ) ) )
            // InternalArchcnl.g:1615:3: ( (lv_concept_0_0= ruleConcept ) ) ( (lv_variable_1_0= ruleVariable ) )
            {
            // InternalArchcnl.g:1615:3: ( (lv_concept_0_0= ruleConcept ) )
            // InternalArchcnl.g:1616:4: (lv_concept_0_0= ruleConcept )
            {
            // InternalArchcnl.g:1616:4: (lv_concept_0_0= ruleConcept )
            // InternalArchcnl.g:1617:5: lv_concept_0_0= ruleConcept
            {

            					newCompositeNode(grammarAccess.getVariableStatementAccess().getConceptConceptParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_26);
            lv_concept_0_0=ruleConcept();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getVariableStatementRule());
            					}
            					set(
            						current,
            						"concept",
            						lv_concept_0_0,
            						"org.architecture.cnl.Archcnl.Concept");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalArchcnl.g:1634:3: ( (lv_variable_1_0= ruleVariable ) )
            // InternalArchcnl.g:1635:4: (lv_variable_1_0= ruleVariable )
            {
            // InternalArchcnl.g:1635:4: (lv_variable_1_0= ruleVariable )
            // InternalArchcnl.g:1636:5: lv_variable_1_0= ruleVariable
            {

            					newCompositeNode(grammarAccess.getVariableStatementAccess().getVariableVariableParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_variable_1_0=ruleVariable();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getVariableStatementRule());
            					}
            					set(
            						current,
            						"variable",
            						lv_variable_1_0,
            						"org.architecture.cnl.Archcnl.Variable");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariableStatement"


    // $ANTLR start "entryRuleDataStatement"
    // InternalArchcnl.g:1657:1: entryRuleDataStatement returns [EObject current=null] : iv_ruleDataStatement= ruleDataStatement EOF ;
    public final EObject entryRuleDataStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataStatement = null;


        try {
            // InternalArchcnl.g:1657:54: (iv_ruleDataStatement= ruleDataStatement EOF )
            // InternalArchcnl.g:1658:2: iv_ruleDataStatement= ruleDataStatement EOF
            {
             newCompositeNode(grammarAccess.getDataStatementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDataStatement=ruleDataStatement();

            state._fsp--;

             current =iv_ruleDataStatement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDataStatement"


    // $ANTLR start "ruleDataStatement"
    // InternalArchcnl.g:1664:1: ruleDataStatement returns [EObject current=null] : ( ( (lv_stringValue_0_0= RULE_STRING ) ) | ( (lv_intValue_1_0= RULE_INT ) ) ) ;
    public final EObject ruleDataStatement() throws RecognitionException {
        EObject current = null;

        Token lv_stringValue_0_0=null;
        Token lv_intValue_1_0=null;


        	enterRule();

        try {
            // InternalArchcnl.g:1670:2: ( ( ( (lv_stringValue_0_0= RULE_STRING ) ) | ( (lv_intValue_1_0= RULE_INT ) ) ) )
            // InternalArchcnl.g:1671:2: ( ( (lv_stringValue_0_0= RULE_STRING ) ) | ( (lv_intValue_1_0= RULE_INT ) ) )
            {
            // InternalArchcnl.g:1671:2: ( ( (lv_stringValue_0_0= RULE_STRING ) ) | ( (lv_intValue_1_0= RULE_INT ) ) )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==RULE_STRING) ) {
                alt17=1;
            }
            else if ( (LA17_0==RULE_INT) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // InternalArchcnl.g:1672:3: ( (lv_stringValue_0_0= RULE_STRING ) )
                    {
                    // InternalArchcnl.g:1672:3: ( (lv_stringValue_0_0= RULE_STRING ) )
                    // InternalArchcnl.g:1673:4: (lv_stringValue_0_0= RULE_STRING )
                    {
                    // InternalArchcnl.g:1673:4: (lv_stringValue_0_0= RULE_STRING )
                    // InternalArchcnl.g:1674:5: lv_stringValue_0_0= RULE_STRING
                    {
                    lv_stringValue_0_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    					newLeafNode(lv_stringValue_0_0, grammarAccess.getDataStatementAccess().getStringValueSTRINGTerminalRuleCall_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getDataStatementRule());
                    					}
                    					setWithLastConsumed(
                    						current,
                    						"stringValue",
                    						lv_stringValue_0_0,
                    						"org.eclipse.xtext.common.Terminals.STRING");
                    				

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1691:3: ( (lv_intValue_1_0= RULE_INT ) )
                    {
                    // InternalArchcnl.g:1691:3: ( (lv_intValue_1_0= RULE_INT ) )
                    // InternalArchcnl.g:1692:4: (lv_intValue_1_0= RULE_INT )
                    {
                    // InternalArchcnl.g:1692:4: (lv_intValue_1_0= RULE_INT )
                    // InternalArchcnl.g:1693:5: lv_intValue_1_0= RULE_INT
                    {
                    lv_intValue_1_0=(Token)match(input,RULE_INT,FOLLOW_2); 

                    					newLeafNode(lv_intValue_1_0, grammarAccess.getDataStatementAccess().getIntValueINTTerminalRuleCall_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getDataStatementRule());
                    					}
                    					setWithLastConsumed(
                    						current,
                    						"intValue",
                    						lv_intValue_1_0,
                    						"org.eclipse.xtext.common.Terminals.INT");
                    				

                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDataStatement"


    // $ANTLR start "entryRuleFactStatement"
    // InternalArchcnl.g:1713:1: entryRuleFactStatement returns [EObject current=null] : iv_ruleFactStatement= ruleFactStatement EOF ;
    public final EObject entryRuleFactStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFactStatement = null;


        try {
            // InternalArchcnl.g:1713:54: (iv_ruleFactStatement= ruleFactStatement EOF )
            // InternalArchcnl.g:1714:2: iv_ruleFactStatement= ruleFactStatement EOF
            {
             newCompositeNode(grammarAccess.getFactStatementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFactStatement=ruleFactStatement();

            state._fsp--;

             current =iv_ruleFactStatement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFactStatement"


    // $ANTLR start "ruleFactStatement"
    // InternalArchcnl.g:1720:1: ruleFactStatement returns [EObject current=null] : ( ( ( (lv_assertion_0_0= ruleConceptAssertion ) ) | ( (lv_assertion_1_0= ruleRoleAssertion ) ) ) otherlv_2= '.' ) ;
    public final EObject ruleFactStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_assertion_0_0 = null;

        EObject lv_assertion_1_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1726:2: ( ( ( ( (lv_assertion_0_0= ruleConceptAssertion ) ) | ( (lv_assertion_1_0= ruleRoleAssertion ) ) ) otherlv_2= '.' ) )
            // InternalArchcnl.g:1727:2: ( ( ( (lv_assertion_0_0= ruleConceptAssertion ) ) | ( (lv_assertion_1_0= ruleRoleAssertion ) ) ) otherlv_2= '.' )
            {
            // InternalArchcnl.g:1727:2: ( ( ( (lv_assertion_0_0= ruleConceptAssertion ) ) | ( (lv_assertion_1_0= ruleRoleAssertion ) ) ) otherlv_2= '.' )
            // InternalArchcnl.g:1728:3: ( ( (lv_assertion_0_0= ruleConceptAssertion ) ) | ( (lv_assertion_1_0= ruleRoleAssertion ) ) ) otherlv_2= '.'
            {
            // InternalArchcnl.g:1728:3: ( ( (lv_assertion_0_0= ruleConceptAssertion ) ) | ( (lv_assertion_1_0= ruleRoleAssertion ) ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_NAME) ) {
                int LA18_1 = input.LA(2);

                if ( (LA18_1==RULE_RELATION_NAME) ) {
                    alt18=2;
                }
                else if ( (LA18_1==40) ) {
                    alt18=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // InternalArchcnl.g:1729:4: ( (lv_assertion_0_0= ruleConceptAssertion ) )
                    {
                    // InternalArchcnl.g:1729:4: ( (lv_assertion_0_0= ruleConceptAssertion ) )
                    // InternalArchcnl.g:1730:5: (lv_assertion_0_0= ruleConceptAssertion )
                    {
                    // InternalArchcnl.g:1730:5: (lv_assertion_0_0= ruleConceptAssertion )
                    // InternalArchcnl.g:1731:6: lv_assertion_0_0= ruleConceptAssertion
                    {

                    						newCompositeNode(grammarAccess.getFactStatementAccess().getAssertionConceptAssertionParserRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_assertion_0_0=ruleConceptAssertion();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFactStatementRule());
                    						}
                    						set(
                    							current,
                    							"assertion",
                    							lv_assertion_0_0,
                    							"org.architecture.cnl.Archcnl.ConceptAssertion");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1749:4: ( (lv_assertion_1_0= ruleRoleAssertion ) )
                    {
                    // InternalArchcnl.g:1749:4: ( (lv_assertion_1_0= ruleRoleAssertion ) )
                    // InternalArchcnl.g:1750:5: (lv_assertion_1_0= ruleRoleAssertion )
                    {
                    // InternalArchcnl.g:1750:5: (lv_assertion_1_0= ruleRoleAssertion )
                    // InternalArchcnl.g:1751:6: lv_assertion_1_0= ruleRoleAssertion
                    {

                    						newCompositeNode(grammarAccess.getFactStatementAccess().getAssertionRoleAssertionParserRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_assertion_1_0=ruleRoleAssertion();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFactStatementRule());
                    						}
                    						set(
                    							current,
                    							"assertion",
                    							lv_assertion_1_0,
                    							"org.architecture.cnl.Archcnl.RoleAssertion");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getFactStatementAccess().getFullStopKeyword_1());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFactStatement"


    // $ANTLR start "entryRuleConceptAssertion"
    // InternalArchcnl.g:1777:1: entryRuleConceptAssertion returns [EObject current=null] : iv_ruleConceptAssertion= ruleConceptAssertion EOF ;
    public final EObject entryRuleConceptAssertion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConceptAssertion = null;


        try {
            // InternalArchcnl.g:1777:57: (iv_ruleConceptAssertion= ruleConceptAssertion EOF )
            // InternalArchcnl.g:1778:2: iv_ruleConceptAssertion= ruleConceptAssertion EOF
            {
             newCompositeNode(grammarAccess.getConceptAssertionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConceptAssertion=ruleConceptAssertion();

            state._fsp--;

             current =iv_ruleConceptAssertion; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConceptAssertion"


    // $ANTLR start "ruleConceptAssertion"
    // InternalArchcnl.g:1784:1: ruleConceptAssertion returns [EObject current=null] : ( ( (lv_individual_0_0= RULE_NAME ) ) otherlv_1= 'is' (otherlv_2= 'a' | otherlv_3= 'an' ) ( (lv_concept_4_0= ruleConcept ) ) otherlv_5= '.' ) ;
    public final EObject ruleConceptAssertion() throws RecognitionException {
        EObject current = null;

        Token lv_individual_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_concept_4_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1790:2: ( ( ( (lv_individual_0_0= RULE_NAME ) ) otherlv_1= 'is' (otherlv_2= 'a' | otherlv_3= 'an' ) ( (lv_concept_4_0= ruleConcept ) ) otherlv_5= '.' ) )
            // InternalArchcnl.g:1791:2: ( ( (lv_individual_0_0= RULE_NAME ) ) otherlv_1= 'is' (otherlv_2= 'a' | otherlv_3= 'an' ) ( (lv_concept_4_0= ruleConcept ) ) otherlv_5= '.' )
            {
            // InternalArchcnl.g:1791:2: ( ( (lv_individual_0_0= RULE_NAME ) ) otherlv_1= 'is' (otherlv_2= 'a' | otherlv_3= 'an' ) ( (lv_concept_4_0= ruleConcept ) ) otherlv_5= '.' )
            // InternalArchcnl.g:1792:3: ( (lv_individual_0_0= RULE_NAME ) ) otherlv_1= 'is' (otherlv_2= 'a' | otherlv_3= 'an' ) ( (lv_concept_4_0= ruleConcept ) ) otherlv_5= '.'
            {
            // InternalArchcnl.g:1792:3: ( (lv_individual_0_0= RULE_NAME ) )
            // InternalArchcnl.g:1793:4: (lv_individual_0_0= RULE_NAME )
            {
            // InternalArchcnl.g:1793:4: (lv_individual_0_0= RULE_NAME )
            // InternalArchcnl.g:1794:5: lv_individual_0_0= RULE_NAME
            {
            lv_individual_0_0=(Token)match(input,RULE_NAME,FOLLOW_27); 

            					newLeafNode(lv_individual_0_0, grammarAccess.getConceptAssertionAccess().getIndividualNAMETerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConceptAssertionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"individual",
            						lv_individual_0_0,
            						"org.architecture.cnl.Archcnl.NAME");
            				

            }


            }

            otherlv_1=(Token)match(input,40,FOLLOW_28); 

            			newLeafNode(otherlv_1, grammarAccess.getConceptAssertionAccess().getIsKeyword_1());
            		
            // InternalArchcnl.g:1814:3: (otherlv_2= 'a' | otherlv_3= 'an' )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==35) ) {
                alt19=1;
            }
            else if ( (LA19_0==36) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // InternalArchcnl.g:1815:4: otherlv_2= 'a'
                    {
                    otherlv_2=(Token)match(input,35,FOLLOW_4); 

                    				newLeafNode(otherlv_2, grammarAccess.getConceptAssertionAccess().getAKeyword_2_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1820:4: otherlv_3= 'an'
                    {
                    otherlv_3=(Token)match(input,36,FOLLOW_4); 

                    				newLeafNode(otherlv_3, grammarAccess.getConceptAssertionAccess().getAnKeyword_2_1());
                    			

                    }
                    break;

            }

            // InternalArchcnl.g:1825:3: ( (lv_concept_4_0= ruleConcept ) )
            // InternalArchcnl.g:1826:4: (lv_concept_4_0= ruleConcept )
            {
            // InternalArchcnl.g:1826:4: (lv_concept_4_0= ruleConcept )
            // InternalArchcnl.g:1827:5: lv_concept_4_0= ruleConcept
            {

            					newCompositeNode(grammarAccess.getConceptAssertionAccess().getConceptConceptParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_8);
            lv_concept_4_0=ruleConcept();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConceptAssertionRule());
            					}
            					set(
            						current,
            						"concept",
            						lv_concept_4_0,
            						"org.architecture.cnl.Archcnl.Concept");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getConceptAssertionAccess().getFullStopKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConceptAssertion"


    // $ANTLR start "entryRuleRoleAssertion"
    // InternalArchcnl.g:1852:1: entryRuleRoleAssertion returns [EObject current=null] : iv_ruleRoleAssertion= ruleRoleAssertion EOF ;
    public final EObject entryRuleRoleAssertion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRoleAssertion = null;


        try {
            // InternalArchcnl.g:1852:54: (iv_ruleRoleAssertion= ruleRoleAssertion EOF )
            // InternalArchcnl.g:1853:2: iv_ruleRoleAssertion= ruleRoleAssertion EOF
            {
             newCompositeNode(grammarAccess.getRoleAssertionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRoleAssertion=ruleRoleAssertion();

            state._fsp--;

             current =iv_ruleRoleAssertion; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRoleAssertion"


    // $ANTLR start "ruleRoleAssertion"
    // InternalArchcnl.g:1859:1: ruleRoleAssertion returns [EObject current=null] : (this_ObjectPropertyAssertion_0= ruleObjectPropertyAssertion | (this_DatatypePropertyAssertion_1= ruleDatatypePropertyAssertion otherlv_2= '.' ) ) ;
    public final EObject ruleRoleAssertion() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_ObjectPropertyAssertion_0 = null;

        EObject this_DatatypePropertyAssertion_1 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1865:2: ( (this_ObjectPropertyAssertion_0= ruleObjectPropertyAssertion | (this_DatatypePropertyAssertion_1= ruleDatatypePropertyAssertion otherlv_2= '.' ) ) )
            // InternalArchcnl.g:1866:2: (this_ObjectPropertyAssertion_0= ruleObjectPropertyAssertion | (this_DatatypePropertyAssertion_1= ruleDatatypePropertyAssertion otherlv_2= '.' ) )
            {
            // InternalArchcnl.g:1866:2: (this_ObjectPropertyAssertion_0= ruleObjectPropertyAssertion | (this_DatatypePropertyAssertion_1= ruleDatatypePropertyAssertion otherlv_2= '.' ) )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==RULE_NAME) ) {
                int LA20_1 = input.LA(2);

                if ( (LA20_1==RULE_RELATION_NAME) ) {
                    int LA20_2 = input.LA(3);

                    if ( (LA20_2==34) ) {
                        int LA20_3 = input.LA(4);

                        if ( ((LA20_3>=35 && LA20_3<=36)) ) {
                            alt20=1;
                        }
                        else if ( (LA20_3==RULE_INT||LA20_3==RULE_STRING) ) {
                            alt20=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 20, 3, input);

                            throw nvae;
                        }
                    }
                    else if ( ((LA20_2>=35 && LA20_2<=36)) ) {
                        alt20=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 20, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // InternalArchcnl.g:1867:3: this_ObjectPropertyAssertion_0= ruleObjectPropertyAssertion
                    {

                    			newCompositeNode(grammarAccess.getRoleAssertionAccess().getObjectPropertyAssertionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_ObjectPropertyAssertion_0=ruleObjectPropertyAssertion();

                    state._fsp--;


                    			current = this_ObjectPropertyAssertion_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1876:3: (this_DatatypePropertyAssertion_1= ruleDatatypePropertyAssertion otherlv_2= '.' )
                    {
                    // InternalArchcnl.g:1876:3: (this_DatatypePropertyAssertion_1= ruleDatatypePropertyAssertion otherlv_2= '.' )
                    // InternalArchcnl.g:1877:4: this_DatatypePropertyAssertion_1= ruleDatatypePropertyAssertion otherlv_2= '.'
                    {

                    				newCompositeNode(grammarAccess.getRoleAssertionAccess().getDatatypePropertyAssertionParserRuleCall_1_0());
                    			
                    pushFollow(FOLLOW_8);
                    this_DatatypePropertyAssertion_1=ruleDatatypePropertyAssertion();

                    state._fsp--;


                    				current = this_DatatypePropertyAssertion_1;
                    				afterParserOrEnumRuleCall();
                    			
                    otherlv_2=(Token)match(input,17,FOLLOW_2); 

                    				newLeafNode(otherlv_2, grammarAccess.getRoleAssertionAccess().getFullStopKeyword_1_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRoleAssertion"


    // $ANTLR start "entryRuleDatatypePropertyAssertion"
    // InternalArchcnl.g:1894:1: entryRuleDatatypePropertyAssertion returns [EObject current=null] : iv_ruleDatatypePropertyAssertion= ruleDatatypePropertyAssertion EOF ;
    public final EObject entryRuleDatatypePropertyAssertion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDatatypePropertyAssertion = null;


        try {
            // InternalArchcnl.g:1894:66: (iv_ruleDatatypePropertyAssertion= ruleDatatypePropertyAssertion EOF )
            // InternalArchcnl.g:1895:2: iv_ruleDatatypePropertyAssertion= ruleDatatypePropertyAssertion EOF
            {
             newCompositeNode(grammarAccess.getDatatypePropertyAssertionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDatatypePropertyAssertion=ruleDatatypePropertyAssertion();

            state._fsp--;

             current =iv_ruleDatatypePropertyAssertion; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDatatypePropertyAssertion"


    // $ANTLR start "ruleDatatypePropertyAssertion"
    // InternalArchcnl.g:1901:1: ruleDatatypePropertyAssertion returns [EObject current=null] : ( ( (lv_individual_0_0= RULE_NAME ) ) ( (lv_relation_1_0= ruleDatatypeRelation ) ) ( ( (lv_string_2_0= RULE_STRING ) ) | ( (lv_int_3_0= RULE_INT ) ) ) ) ;
    public final EObject ruleDatatypePropertyAssertion() throws RecognitionException {
        EObject current = null;

        Token lv_individual_0_0=null;
        Token lv_string_2_0=null;
        Token lv_int_3_0=null;
        EObject lv_relation_1_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:1907:2: ( ( ( (lv_individual_0_0= RULE_NAME ) ) ( (lv_relation_1_0= ruleDatatypeRelation ) ) ( ( (lv_string_2_0= RULE_STRING ) ) | ( (lv_int_3_0= RULE_INT ) ) ) ) )
            // InternalArchcnl.g:1908:2: ( ( (lv_individual_0_0= RULE_NAME ) ) ( (lv_relation_1_0= ruleDatatypeRelation ) ) ( ( (lv_string_2_0= RULE_STRING ) ) | ( (lv_int_3_0= RULE_INT ) ) ) )
            {
            // InternalArchcnl.g:1908:2: ( ( (lv_individual_0_0= RULE_NAME ) ) ( (lv_relation_1_0= ruleDatatypeRelation ) ) ( ( (lv_string_2_0= RULE_STRING ) ) | ( (lv_int_3_0= RULE_INT ) ) ) )
            // InternalArchcnl.g:1909:3: ( (lv_individual_0_0= RULE_NAME ) ) ( (lv_relation_1_0= ruleDatatypeRelation ) ) ( ( (lv_string_2_0= RULE_STRING ) ) | ( (lv_int_3_0= RULE_INT ) ) )
            {
            // InternalArchcnl.g:1909:3: ( (lv_individual_0_0= RULE_NAME ) )
            // InternalArchcnl.g:1910:4: (lv_individual_0_0= RULE_NAME )
            {
            // InternalArchcnl.g:1910:4: (lv_individual_0_0= RULE_NAME )
            // InternalArchcnl.g:1911:5: lv_individual_0_0= RULE_NAME
            {
            lv_individual_0_0=(Token)match(input,RULE_NAME,FOLLOW_7); 

            					newLeafNode(lv_individual_0_0, grammarAccess.getDatatypePropertyAssertionAccess().getIndividualNAMETerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDatatypePropertyAssertionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"individual",
            						lv_individual_0_0,
            						"org.architecture.cnl.Archcnl.NAME");
            				

            }


            }

            // InternalArchcnl.g:1927:3: ( (lv_relation_1_0= ruleDatatypeRelation ) )
            // InternalArchcnl.g:1928:4: (lv_relation_1_0= ruleDatatypeRelation )
            {
            // InternalArchcnl.g:1928:4: (lv_relation_1_0= ruleDatatypeRelation )
            // InternalArchcnl.g:1929:5: lv_relation_1_0= ruleDatatypeRelation
            {

            					newCompositeNode(grammarAccess.getDatatypePropertyAssertionAccess().getRelationDatatypeRelationParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_19);
            lv_relation_1_0=ruleDatatypeRelation();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDatatypePropertyAssertionRule());
            					}
            					set(
            						current,
            						"relation",
            						lv_relation_1_0,
            						"org.architecture.cnl.Archcnl.DatatypeRelation");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalArchcnl.g:1946:3: ( ( (lv_string_2_0= RULE_STRING ) ) | ( (lv_int_3_0= RULE_INT ) ) )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_STRING) ) {
                alt21=1;
            }
            else if ( (LA21_0==RULE_INT) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // InternalArchcnl.g:1947:4: ( (lv_string_2_0= RULE_STRING ) )
                    {
                    // InternalArchcnl.g:1947:4: ( (lv_string_2_0= RULE_STRING ) )
                    // InternalArchcnl.g:1948:5: (lv_string_2_0= RULE_STRING )
                    {
                    // InternalArchcnl.g:1948:5: (lv_string_2_0= RULE_STRING )
                    // InternalArchcnl.g:1949:6: lv_string_2_0= RULE_STRING
                    {
                    lv_string_2_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    						newLeafNode(lv_string_2_0, grammarAccess.getDatatypePropertyAssertionAccess().getStringSTRINGTerminalRuleCall_2_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDatatypePropertyAssertionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"string",
                    							lv_string_2_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1966:4: ( (lv_int_3_0= RULE_INT ) )
                    {
                    // InternalArchcnl.g:1966:4: ( (lv_int_3_0= RULE_INT ) )
                    // InternalArchcnl.g:1967:5: (lv_int_3_0= RULE_INT )
                    {
                    // InternalArchcnl.g:1967:5: (lv_int_3_0= RULE_INT )
                    // InternalArchcnl.g:1968:6: lv_int_3_0= RULE_INT
                    {
                    lv_int_3_0=(Token)match(input,RULE_INT,FOLLOW_2); 

                    						newLeafNode(lv_int_3_0, grammarAccess.getDatatypePropertyAssertionAccess().getIntINTTerminalRuleCall_2_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDatatypePropertyAssertionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"int",
                    							lv_int_3_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDatatypePropertyAssertion"


    // $ANTLR start "entryRuleObjectPropertyAssertion"
    // InternalArchcnl.g:1989:1: entryRuleObjectPropertyAssertion returns [EObject current=null] : iv_ruleObjectPropertyAssertion= ruleObjectPropertyAssertion EOF ;
    public final EObject entryRuleObjectPropertyAssertion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleObjectPropertyAssertion = null;


        try {
            // InternalArchcnl.g:1989:64: (iv_ruleObjectPropertyAssertion= ruleObjectPropertyAssertion EOF )
            // InternalArchcnl.g:1990:2: iv_ruleObjectPropertyAssertion= ruleObjectPropertyAssertion EOF
            {
             newCompositeNode(grammarAccess.getObjectPropertyAssertionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleObjectPropertyAssertion=ruleObjectPropertyAssertion();

            state._fsp--;

             current =iv_ruleObjectPropertyAssertion; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleObjectPropertyAssertion"


    // $ANTLR start "ruleObjectPropertyAssertion"
    // InternalArchcnl.g:1996:1: ruleObjectPropertyAssertion returns [EObject current=null] : ( ( (lv_individual_0_0= RULE_NAME ) ) ( (lv_relation_1_0= ruleRelation ) ) (otherlv_2= 'a' | otherlv_3= 'an' ) ( (lv_concept_4_0= ruleConcept ) ) ) ;
    public final EObject ruleObjectPropertyAssertion() throws RecognitionException {
        EObject current = null;

        Token lv_individual_0_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject lv_relation_1_0 = null;

        EObject lv_concept_4_0 = null;



        	enterRule();

        try {
            // InternalArchcnl.g:2002:2: ( ( ( (lv_individual_0_0= RULE_NAME ) ) ( (lv_relation_1_0= ruleRelation ) ) (otherlv_2= 'a' | otherlv_3= 'an' ) ( (lv_concept_4_0= ruleConcept ) ) ) )
            // InternalArchcnl.g:2003:2: ( ( (lv_individual_0_0= RULE_NAME ) ) ( (lv_relation_1_0= ruleRelation ) ) (otherlv_2= 'a' | otherlv_3= 'an' ) ( (lv_concept_4_0= ruleConcept ) ) )
            {
            // InternalArchcnl.g:2003:2: ( ( (lv_individual_0_0= RULE_NAME ) ) ( (lv_relation_1_0= ruleRelation ) ) (otherlv_2= 'a' | otherlv_3= 'an' ) ( (lv_concept_4_0= ruleConcept ) ) )
            // InternalArchcnl.g:2004:3: ( (lv_individual_0_0= RULE_NAME ) ) ( (lv_relation_1_0= ruleRelation ) ) (otherlv_2= 'a' | otherlv_3= 'an' ) ( (lv_concept_4_0= ruleConcept ) )
            {
            // InternalArchcnl.g:2004:3: ( (lv_individual_0_0= RULE_NAME ) )
            // InternalArchcnl.g:2005:4: (lv_individual_0_0= RULE_NAME )
            {
            // InternalArchcnl.g:2005:4: (lv_individual_0_0= RULE_NAME )
            // InternalArchcnl.g:2006:5: lv_individual_0_0= RULE_NAME
            {
            lv_individual_0_0=(Token)match(input,RULE_NAME,FOLLOW_7); 

            					newLeafNode(lv_individual_0_0, grammarAccess.getObjectPropertyAssertionAccess().getIndividualNAMETerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getObjectPropertyAssertionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"individual",
            						lv_individual_0_0,
            						"org.architecture.cnl.Archcnl.NAME");
            				

            }


            }

            // InternalArchcnl.g:2022:3: ( (lv_relation_1_0= ruleRelation ) )
            // InternalArchcnl.g:2023:4: (lv_relation_1_0= ruleRelation )
            {
            // InternalArchcnl.g:2023:4: (lv_relation_1_0= ruleRelation )
            // InternalArchcnl.g:2024:5: lv_relation_1_0= ruleRelation
            {

            					newCompositeNode(grammarAccess.getObjectPropertyAssertionAccess().getRelationRelationParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_28);
            lv_relation_1_0=ruleRelation();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getObjectPropertyAssertionRule());
            					}
            					set(
            						current,
            						"relation",
            						lv_relation_1_0,
            						"org.architecture.cnl.Archcnl.Relation");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalArchcnl.g:2041:3: (otherlv_2= 'a' | otherlv_3= 'an' )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==35) ) {
                alt22=1;
            }
            else if ( (LA22_0==36) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalArchcnl.g:2042:4: otherlv_2= 'a'
                    {
                    otherlv_2=(Token)match(input,35,FOLLOW_4); 

                    				newLeafNode(otherlv_2, grammarAccess.getObjectPropertyAssertionAccess().getAKeyword_2_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:2047:4: otherlv_3= 'an'
                    {
                    otherlv_3=(Token)match(input,36,FOLLOW_4); 

                    				newLeafNode(otherlv_3, grammarAccess.getObjectPropertyAssertionAccess().getAnKeyword_2_1());
                    			

                    }
                    break;

            }

            // InternalArchcnl.g:2052:3: ( (lv_concept_4_0= ruleConcept ) )
            // InternalArchcnl.g:2053:4: (lv_concept_4_0= ruleConcept )
            {
            // InternalArchcnl.g:2053:4: (lv_concept_4_0= ruleConcept )
            // InternalArchcnl.g:2054:5: lv_concept_4_0= ruleConcept
            {

            					newCompositeNode(grammarAccess.getObjectPropertyAssertionAccess().getConceptConceptParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_concept_4_0=ruleConcept();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getObjectPropertyAssertionRule());
            					}
            					set(
            						current,
            						"concept",
            						lv_concept_4_0,
            						"org.architecture.cnl.Archcnl.Concept");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleObjectPropertyAssertion"


    // $ANTLR start "entryRuleConcept"
    // InternalArchcnl.g:2075:1: entryRuleConcept returns [EObject current=null] : iv_ruleConcept= ruleConcept EOF ;
    public final EObject entryRuleConcept() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConcept = null;


        try {
            // InternalArchcnl.g:2075:48: (iv_ruleConcept= ruleConcept EOF )
            // InternalArchcnl.g:2076:2: iv_ruleConcept= ruleConcept EOF
            {
             newCompositeNode(grammarAccess.getConceptRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConcept=ruleConcept();

            state._fsp--;

             current =iv_ruleConcept; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConcept"


    // $ANTLR start "ruleConcept"
    // InternalArchcnl.g:2082:1: ruleConcept returns [EObject current=null] : ( (lv_conceptName_0_0= RULE_NAME ) ) ;
    public final EObject ruleConcept() throws RecognitionException {
        EObject current = null;

        Token lv_conceptName_0_0=null;


        	enterRule();

        try {
            // InternalArchcnl.g:2088:2: ( ( (lv_conceptName_0_0= RULE_NAME ) ) )
            // InternalArchcnl.g:2089:2: ( (lv_conceptName_0_0= RULE_NAME ) )
            {
            // InternalArchcnl.g:2089:2: ( (lv_conceptName_0_0= RULE_NAME ) )
            // InternalArchcnl.g:2090:3: (lv_conceptName_0_0= RULE_NAME )
            {
            // InternalArchcnl.g:2090:3: (lv_conceptName_0_0= RULE_NAME )
            // InternalArchcnl.g:2091:4: lv_conceptName_0_0= RULE_NAME
            {
            lv_conceptName_0_0=(Token)match(input,RULE_NAME,FOLLOW_2); 

            				newLeafNode(lv_conceptName_0_0, grammarAccess.getConceptAccess().getConceptNameNAMETerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getConceptRule());
            				}
            				setWithLastConsumed(
            					current,
            					"conceptName",
            					lv_conceptName_0_0,
            					"org.architecture.cnl.Archcnl.NAME");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConcept"


    // $ANTLR start "entryRuleVariable"
    // InternalArchcnl.g:2110:1: entryRuleVariable returns [EObject current=null] : iv_ruleVariable= ruleVariable EOF ;
    public final EObject entryRuleVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariable = null;


        try {
            // InternalArchcnl.g:2110:49: (iv_ruleVariable= ruleVariable EOF )
            // InternalArchcnl.g:2111:2: iv_ruleVariable= ruleVariable EOF
            {
             newCompositeNode(grammarAccess.getVariableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariable=ruleVariable();

            state._fsp--;

             current =iv_ruleVariable; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariable"


    // $ANTLR start "ruleVariable"
    // InternalArchcnl.g:2117:1: ruleVariable returns [EObject current=null] : ( (lv_name_0_0= RULE_VARIABLE_NAME ) ) ;
    public final EObject ruleVariable() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalArchcnl.g:2123:2: ( ( (lv_name_0_0= RULE_VARIABLE_NAME ) ) )
            // InternalArchcnl.g:2124:2: ( (lv_name_0_0= RULE_VARIABLE_NAME ) )
            {
            // InternalArchcnl.g:2124:2: ( (lv_name_0_0= RULE_VARIABLE_NAME ) )
            // InternalArchcnl.g:2125:3: (lv_name_0_0= RULE_VARIABLE_NAME )
            {
            // InternalArchcnl.g:2125:3: (lv_name_0_0= RULE_VARIABLE_NAME )
            // InternalArchcnl.g:2126:4: lv_name_0_0= RULE_VARIABLE_NAME
            {
            lv_name_0_0=(Token)match(input,RULE_VARIABLE_NAME,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getVariableAccess().getNameVARIABLE_NAMETerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getVariableRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.architecture.cnl.Archcnl.VARIABLE_NAME");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariable"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x000000000418C082L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000001800000080L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000011010000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000300000002L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x00000018E0000080L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000100000020L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000008100000020L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000018000000D0L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000001800000000L});

}