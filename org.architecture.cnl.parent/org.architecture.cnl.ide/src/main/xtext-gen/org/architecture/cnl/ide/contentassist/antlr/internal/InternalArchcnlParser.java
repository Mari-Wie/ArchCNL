package org.architecture.cnl.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.architecture.cnl.services.ArchcnlGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalArchcnlParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_INT", "RULE_RELATION_NAME", "RULE_STRING", "RULE_NAME", "RULE_VARIABLE_NAME", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'at-most'", "'at-least'", "'exactly'", "'a'", "'an'", "'Every'", "'.'", "'No'", "'can'", "'anything'", "'Nothing'", "','", "'then'", "'it'", "'must'", "'this'", "'be'", "'and'", "'or'", "'equal-to'", "'that'", "'('", "')'", "'is'", "'If'", "'Only'", "'can-only'"
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

    	public void setGrammarAccess(ArchcnlGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleModel"
    // InternalArchcnl.g:53:1: entryRuleModel : ruleModel EOF ;
    public final void entryRuleModel() throws RecognitionException {
        try {
            // InternalArchcnl.g:54:1: ( ruleModel EOF )
            // InternalArchcnl.g:55:1: ruleModel EOF
            {
             before(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            ruleModel();

            state._fsp--;

             after(grammarAccess.getModelRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalArchcnl.g:62:1: ruleModel : ( ( rule__Model__SentenceAssignment )* ) ;
    public final void ruleModel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:66:2: ( ( ( rule__Model__SentenceAssignment )* ) )
            // InternalArchcnl.g:67:2: ( ( rule__Model__SentenceAssignment )* )
            {
            // InternalArchcnl.g:67:2: ( ( rule__Model__SentenceAssignment )* )
            // InternalArchcnl.g:68:3: ( rule__Model__SentenceAssignment )*
            {
             before(grammarAccess.getModelAccess().getSentenceAssignment()); 
            // InternalArchcnl.g:69:3: ( rule__Model__SentenceAssignment )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_NAME||LA1_0==19||LA1_0==21||LA1_0==24||(LA1_0>=38 && LA1_0<=39)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalArchcnl.g:69:4: rule__Model__SentenceAssignment
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__Model__SentenceAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             after(grammarAccess.getModelAccess().getSentenceAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleSentence"
    // InternalArchcnl.g:78:1: entryRuleSentence : ruleSentence EOF ;
    public final void entryRuleSentence() throws RecognitionException {
        try {
            // InternalArchcnl.g:79:1: ( ruleSentence EOF )
            // InternalArchcnl.g:80:1: ruleSentence EOF
            {
             before(grammarAccess.getSentenceRule()); 
            pushFollow(FOLLOW_1);
            ruleSentence();

            state._fsp--;

             after(grammarAccess.getSentenceRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSentence"


    // $ANTLR start "ruleSentence"
    // InternalArchcnl.g:87:1: ruleSentence : ( ( rule__Sentence__Alternatives ) ) ;
    public final void ruleSentence() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:91:2: ( ( ( rule__Sentence__Alternatives ) ) )
            // InternalArchcnl.g:92:2: ( ( rule__Sentence__Alternatives ) )
            {
            // InternalArchcnl.g:92:2: ( ( rule__Sentence__Alternatives ) )
            // InternalArchcnl.g:93:3: ( rule__Sentence__Alternatives )
            {
             before(grammarAccess.getSentenceAccess().getAlternatives()); 
            // InternalArchcnl.g:94:3: ( rule__Sentence__Alternatives )
            // InternalArchcnl.g:94:4: rule__Sentence__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Sentence__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getSentenceAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSentence"


    // $ANTLR start "entryRuleNegationRuleType"
    // InternalArchcnl.g:103:1: entryRuleNegationRuleType : ruleNegationRuleType EOF ;
    public final void entryRuleNegationRuleType() throws RecognitionException {
        try {
            // InternalArchcnl.g:104:1: ( ruleNegationRuleType EOF )
            // InternalArchcnl.g:105:1: ruleNegationRuleType EOF
            {
             before(grammarAccess.getNegationRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            ruleNegationRuleType();

            state._fsp--;

             after(grammarAccess.getNegationRuleTypeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNegationRuleType"


    // $ANTLR start "ruleNegationRuleType"
    // InternalArchcnl.g:112:1: ruleNegationRuleType : ( ( rule__NegationRuleType__Alternatives ) ) ;
    public final void ruleNegationRuleType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:116:2: ( ( ( rule__NegationRuleType__Alternatives ) ) )
            // InternalArchcnl.g:117:2: ( ( rule__NegationRuleType__Alternatives ) )
            {
            // InternalArchcnl.g:117:2: ( ( rule__NegationRuleType__Alternatives ) )
            // InternalArchcnl.g:118:3: ( rule__NegationRuleType__Alternatives )
            {
             before(grammarAccess.getNegationRuleTypeAccess().getAlternatives()); 
            // InternalArchcnl.g:119:3: ( rule__NegationRuleType__Alternatives )
            // InternalArchcnl.g:119:4: rule__NegationRuleType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__NegationRuleType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getNegationRuleTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNegationRuleType"


    // $ANTLR start "entryRuleAnything"
    // InternalArchcnl.g:128:1: entryRuleAnything : ruleAnything EOF ;
    public final void entryRuleAnything() throws RecognitionException {
        try {
            // InternalArchcnl.g:129:1: ( ruleAnything EOF )
            // InternalArchcnl.g:130:1: ruleAnything EOF
            {
             before(grammarAccess.getAnythingRule()); 
            pushFollow(FOLLOW_1);
            ruleAnything();

            state._fsp--;

             after(grammarAccess.getAnythingRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAnything"


    // $ANTLR start "ruleAnything"
    // InternalArchcnl.g:137:1: ruleAnything : ( ( rule__Anything__Group__0 ) ) ;
    public final void ruleAnything() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:141:2: ( ( ( rule__Anything__Group__0 ) ) )
            // InternalArchcnl.g:142:2: ( ( rule__Anything__Group__0 ) )
            {
            // InternalArchcnl.g:142:2: ( ( rule__Anything__Group__0 ) )
            // InternalArchcnl.g:143:3: ( rule__Anything__Group__0 )
            {
             before(grammarAccess.getAnythingAccess().getGroup()); 
            // InternalArchcnl.g:144:3: ( rule__Anything__Group__0 )
            // InternalArchcnl.g:144:4: rule__Anything__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Anything__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAnythingAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAnything"


    // $ANTLR start "entryRuleNothing"
    // InternalArchcnl.g:153:1: entryRuleNothing : ruleNothing EOF ;
    public final void entryRuleNothing() throws RecognitionException {
        try {
            // InternalArchcnl.g:154:1: ( ruleNothing EOF )
            // InternalArchcnl.g:155:1: ruleNothing EOF
            {
             before(grammarAccess.getNothingRule()); 
            pushFollow(FOLLOW_1);
            ruleNothing();

            state._fsp--;

             after(grammarAccess.getNothingRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNothing"


    // $ANTLR start "ruleNothing"
    // InternalArchcnl.g:162:1: ruleNothing : ( ( rule__Nothing__Group__0 ) ) ;
    public final void ruleNothing() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:166:2: ( ( ( rule__Nothing__Group__0 ) ) )
            // InternalArchcnl.g:167:2: ( ( rule__Nothing__Group__0 ) )
            {
            // InternalArchcnl.g:167:2: ( ( rule__Nothing__Group__0 ) )
            // InternalArchcnl.g:168:3: ( rule__Nothing__Group__0 )
            {
             before(grammarAccess.getNothingAccess().getGroup()); 
            // InternalArchcnl.g:169:3: ( rule__Nothing__Group__0 )
            // InternalArchcnl.g:169:4: rule__Nothing__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Nothing__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getNothingAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNothing"


    // $ANTLR start "entryRuleConditionalRuleType"
    // InternalArchcnl.g:178:1: entryRuleConditionalRuleType : ruleConditionalRuleType EOF ;
    public final void entryRuleConditionalRuleType() throws RecognitionException {
        try {
            // InternalArchcnl.g:179:1: ( ruleConditionalRuleType EOF )
            // InternalArchcnl.g:180:1: ruleConditionalRuleType EOF
            {
             before(grammarAccess.getConditionalRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            ruleConditionalRuleType();

            state._fsp--;

             after(grammarAccess.getConditionalRuleTypeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleConditionalRuleType"


    // $ANTLR start "ruleConditionalRuleType"
    // InternalArchcnl.g:187:1: ruleConditionalRuleType : ( ( rule__ConditionalRuleType__Group__0 ) ) ;
    public final void ruleConditionalRuleType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:191:2: ( ( ( rule__ConditionalRuleType__Group__0 ) ) )
            // InternalArchcnl.g:192:2: ( ( rule__ConditionalRuleType__Group__0 ) )
            {
            // InternalArchcnl.g:192:2: ( ( rule__ConditionalRuleType__Group__0 ) )
            // InternalArchcnl.g:193:3: ( rule__ConditionalRuleType__Group__0 )
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getGroup()); 
            // InternalArchcnl.g:194:3: ( rule__ConditionalRuleType__Group__0 )
            // InternalArchcnl.g:194:4: rule__ConditionalRuleType__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getConditionalRuleTypeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleConditionalRuleType"


    // $ANTLR start "entryRuleOnlyCanRuleType"
    // InternalArchcnl.g:203:1: entryRuleOnlyCanRuleType : ruleOnlyCanRuleType EOF ;
    public final void entryRuleOnlyCanRuleType() throws RecognitionException {
        try {
            // InternalArchcnl.g:204:1: ( ruleOnlyCanRuleType EOF )
            // InternalArchcnl.g:205:1: ruleOnlyCanRuleType EOF
            {
             before(grammarAccess.getOnlyCanRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            ruleOnlyCanRuleType();

            state._fsp--;

             after(grammarAccess.getOnlyCanRuleTypeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleOnlyCanRuleType"


    // $ANTLR start "ruleOnlyCanRuleType"
    // InternalArchcnl.g:212:1: ruleOnlyCanRuleType : ( ( rule__OnlyCanRuleType__Group__0 ) ) ;
    public final void ruleOnlyCanRuleType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:216:2: ( ( ( rule__OnlyCanRuleType__Group__0 ) ) )
            // InternalArchcnl.g:217:2: ( ( rule__OnlyCanRuleType__Group__0 ) )
            {
            // InternalArchcnl.g:217:2: ( ( rule__OnlyCanRuleType__Group__0 ) )
            // InternalArchcnl.g:218:3: ( rule__OnlyCanRuleType__Group__0 )
            {
             before(grammarAccess.getOnlyCanRuleTypeAccess().getGroup()); 
            // InternalArchcnl.g:219:3: ( rule__OnlyCanRuleType__Group__0 )
            // InternalArchcnl.g:219:4: rule__OnlyCanRuleType__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__OnlyCanRuleType__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getOnlyCanRuleTypeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleOnlyCanRuleType"


    // $ANTLR start "entryRuleSubConceptRuleType"
    // InternalArchcnl.g:228:1: entryRuleSubConceptRuleType : ruleSubConceptRuleType EOF ;
    public final void entryRuleSubConceptRuleType() throws RecognitionException {
        try {
            // InternalArchcnl.g:229:1: ( ruleSubConceptRuleType EOF )
            // InternalArchcnl.g:230:1: ruleSubConceptRuleType EOF
            {
             before(grammarAccess.getSubConceptRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            ruleSubConceptRuleType();

            state._fsp--;

             after(grammarAccess.getSubConceptRuleTypeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSubConceptRuleType"


    // $ANTLR start "ruleSubConceptRuleType"
    // InternalArchcnl.g:237:1: ruleSubConceptRuleType : ( ( rule__SubConceptRuleType__Group__0 ) ) ;
    public final void ruleSubConceptRuleType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:241:2: ( ( ( rule__SubConceptRuleType__Group__0 ) ) )
            // InternalArchcnl.g:242:2: ( ( rule__SubConceptRuleType__Group__0 ) )
            {
            // InternalArchcnl.g:242:2: ( ( rule__SubConceptRuleType__Group__0 ) )
            // InternalArchcnl.g:243:3: ( rule__SubConceptRuleType__Group__0 )
            {
             before(grammarAccess.getSubConceptRuleTypeAccess().getGroup()); 
            // InternalArchcnl.g:244:3: ( rule__SubConceptRuleType__Group__0 )
            // InternalArchcnl.g:244:4: rule__SubConceptRuleType__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__SubConceptRuleType__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSubConceptRuleTypeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSubConceptRuleType"


    // $ANTLR start "entryRuleMustRuleType"
    // InternalArchcnl.g:253:1: entryRuleMustRuleType : ruleMustRuleType EOF ;
    public final void entryRuleMustRuleType() throws RecognitionException {
        try {
            // InternalArchcnl.g:254:1: ( ruleMustRuleType EOF )
            // InternalArchcnl.g:255:1: ruleMustRuleType EOF
            {
             before(grammarAccess.getMustRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            ruleMustRuleType();

            state._fsp--;

             after(grammarAccess.getMustRuleTypeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMustRuleType"


    // $ANTLR start "ruleMustRuleType"
    // InternalArchcnl.g:262:1: ruleMustRuleType : ( ( rule__MustRuleType__Group__0 ) ) ;
    public final void ruleMustRuleType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:266:2: ( ( ( rule__MustRuleType__Group__0 ) ) )
            // InternalArchcnl.g:267:2: ( ( rule__MustRuleType__Group__0 ) )
            {
            // InternalArchcnl.g:267:2: ( ( rule__MustRuleType__Group__0 ) )
            // InternalArchcnl.g:268:3: ( rule__MustRuleType__Group__0 )
            {
             before(grammarAccess.getMustRuleTypeAccess().getGroup()); 
            // InternalArchcnl.g:269:3: ( rule__MustRuleType__Group__0 )
            // InternalArchcnl.g:269:4: rule__MustRuleType__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__MustRuleType__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getMustRuleTypeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMustRuleType"


    // $ANTLR start "entryRuleObject"
    // InternalArchcnl.g:278:1: entryRuleObject : ruleObject EOF ;
    public final void entryRuleObject() throws RecognitionException {
        try {
            // InternalArchcnl.g:279:1: ( ruleObject EOF )
            // InternalArchcnl.g:280:1: ruleObject EOF
            {
             before(grammarAccess.getObjectRule()); 
            pushFollow(FOLLOW_1);
            ruleObject();

            state._fsp--;

             after(grammarAccess.getObjectRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleObject"


    // $ANTLR start "ruleObject"
    // InternalArchcnl.g:287:1: ruleObject : ( ( rule__Object__Alternatives ) ) ;
    public final void ruleObject() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:291:2: ( ( ( rule__Object__Alternatives ) ) )
            // InternalArchcnl.g:292:2: ( ( rule__Object__Alternatives ) )
            {
            // InternalArchcnl.g:292:2: ( ( rule__Object__Alternatives ) )
            // InternalArchcnl.g:293:3: ( rule__Object__Alternatives )
            {
             before(grammarAccess.getObjectAccess().getAlternatives()); 
            // InternalArchcnl.g:294:3: ( rule__Object__Alternatives )
            // InternalArchcnl.g:294:4: rule__Object__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Object__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getObjectAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleObject"


    // $ANTLR start "entryRuleCanOnlyRuleType"
    // InternalArchcnl.g:303:1: entryRuleCanOnlyRuleType : ruleCanOnlyRuleType EOF ;
    public final void entryRuleCanOnlyRuleType() throws RecognitionException {
        try {
            // InternalArchcnl.g:304:1: ( ruleCanOnlyRuleType EOF )
            // InternalArchcnl.g:305:1: ruleCanOnlyRuleType EOF
            {
             before(grammarAccess.getCanOnlyRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            ruleCanOnlyRuleType();

            state._fsp--;

             after(grammarAccess.getCanOnlyRuleTypeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCanOnlyRuleType"


    // $ANTLR start "ruleCanOnlyRuleType"
    // InternalArchcnl.g:312:1: ruleCanOnlyRuleType : ( ( rule__CanOnlyRuleType__Group__0 ) ) ;
    public final void ruleCanOnlyRuleType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:316:2: ( ( ( rule__CanOnlyRuleType__Group__0 ) ) )
            // InternalArchcnl.g:317:2: ( ( rule__CanOnlyRuleType__Group__0 ) )
            {
            // InternalArchcnl.g:317:2: ( ( rule__CanOnlyRuleType__Group__0 ) )
            // InternalArchcnl.g:318:3: ( rule__CanOnlyRuleType__Group__0 )
            {
             before(grammarAccess.getCanOnlyRuleTypeAccess().getGroup()); 
            // InternalArchcnl.g:319:3: ( rule__CanOnlyRuleType__Group__0 )
            // InternalArchcnl.g:319:4: rule__CanOnlyRuleType__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__CanOnlyRuleType__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getCanOnlyRuleTypeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCanOnlyRuleType"


    // $ANTLR start "entryRuleCardinalityRuleType"
    // InternalArchcnl.g:328:1: entryRuleCardinalityRuleType : ruleCardinalityRuleType EOF ;
    public final void entryRuleCardinalityRuleType() throws RecognitionException {
        try {
            // InternalArchcnl.g:329:1: ( ruleCardinalityRuleType EOF )
            // InternalArchcnl.g:330:1: ruleCardinalityRuleType EOF
            {
             before(grammarAccess.getCardinalityRuleTypeRule()); 
            pushFollow(FOLLOW_1);
            ruleCardinalityRuleType();

            state._fsp--;

             after(grammarAccess.getCardinalityRuleTypeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCardinalityRuleType"


    // $ANTLR start "ruleCardinalityRuleType"
    // InternalArchcnl.g:337:1: ruleCardinalityRuleType : ( ( rule__CardinalityRuleType__Group__0 ) ) ;
    public final void ruleCardinalityRuleType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:341:2: ( ( ( rule__CardinalityRuleType__Group__0 ) ) )
            // InternalArchcnl.g:342:2: ( ( rule__CardinalityRuleType__Group__0 ) )
            {
            // InternalArchcnl.g:342:2: ( ( rule__CardinalityRuleType__Group__0 ) )
            // InternalArchcnl.g:343:3: ( rule__CardinalityRuleType__Group__0 )
            {
             before(grammarAccess.getCardinalityRuleTypeAccess().getGroup()); 
            // InternalArchcnl.g:344:3: ( rule__CardinalityRuleType__Group__0 )
            // InternalArchcnl.g:344:4: rule__CardinalityRuleType__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__CardinalityRuleType__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getCardinalityRuleTypeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCardinalityRuleType"


    // $ANTLR start "entryRuleObjectConceptExpression"
    // InternalArchcnl.g:353:1: entryRuleObjectConceptExpression : ruleObjectConceptExpression EOF ;
    public final void entryRuleObjectConceptExpression() throws RecognitionException {
        try {
            // InternalArchcnl.g:354:1: ( ruleObjectConceptExpression EOF )
            // InternalArchcnl.g:355:1: ruleObjectConceptExpression EOF
            {
             before(grammarAccess.getObjectConceptExpressionRule()); 
            pushFollow(FOLLOW_1);
            ruleObjectConceptExpression();

            state._fsp--;

             after(grammarAccess.getObjectConceptExpressionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleObjectConceptExpression"


    // $ANTLR start "ruleObjectConceptExpression"
    // InternalArchcnl.g:362:1: ruleObjectConceptExpression : ( ( rule__ObjectConceptExpression__Alternatives ) ) ;
    public final void ruleObjectConceptExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:366:2: ( ( ( rule__ObjectConceptExpression__Alternatives ) ) )
            // InternalArchcnl.g:367:2: ( ( rule__ObjectConceptExpression__Alternatives ) )
            {
            // InternalArchcnl.g:367:2: ( ( rule__ObjectConceptExpression__Alternatives ) )
            // InternalArchcnl.g:368:3: ( rule__ObjectConceptExpression__Alternatives )
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getAlternatives()); 
            // InternalArchcnl.g:369:3: ( rule__ObjectConceptExpression__Alternatives )
            // InternalArchcnl.g:369:4: rule__ObjectConceptExpression__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getObjectConceptExpressionAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleObjectConceptExpression"


    // $ANTLR start "entryRuleAndObjectConceptExpression"
    // InternalArchcnl.g:378:1: entryRuleAndObjectConceptExpression : ruleAndObjectConceptExpression EOF ;
    public final void entryRuleAndObjectConceptExpression() throws RecognitionException {
        try {
            // InternalArchcnl.g:379:1: ( ruleAndObjectConceptExpression EOF )
            // InternalArchcnl.g:380:1: ruleAndObjectConceptExpression EOF
            {
             before(grammarAccess.getAndObjectConceptExpressionRule()); 
            pushFollow(FOLLOW_1);
            ruleAndObjectConceptExpression();

            state._fsp--;

             after(grammarAccess.getAndObjectConceptExpressionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAndObjectConceptExpression"


    // $ANTLR start "ruleAndObjectConceptExpression"
    // InternalArchcnl.g:387:1: ruleAndObjectConceptExpression : ( ( rule__AndObjectConceptExpression__Group__0 ) ) ;
    public final void ruleAndObjectConceptExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:391:2: ( ( ( rule__AndObjectConceptExpression__Group__0 ) ) )
            // InternalArchcnl.g:392:2: ( ( rule__AndObjectConceptExpression__Group__0 ) )
            {
            // InternalArchcnl.g:392:2: ( ( rule__AndObjectConceptExpression__Group__0 ) )
            // InternalArchcnl.g:393:3: ( rule__AndObjectConceptExpression__Group__0 )
            {
             before(grammarAccess.getAndObjectConceptExpressionAccess().getGroup()); 
            // InternalArchcnl.g:394:3: ( rule__AndObjectConceptExpression__Group__0 )
            // InternalArchcnl.g:394:4: rule__AndObjectConceptExpression__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__AndObjectConceptExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAndObjectConceptExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAndObjectConceptExpression"


    // $ANTLR start "entryRuleOrObjectConceptExpression"
    // InternalArchcnl.g:403:1: entryRuleOrObjectConceptExpression : ruleOrObjectConceptExpression EOF ;
    public final void entryRuleOrObjectConceptExpression() throws RecognitionException {
        try {
            // InternalArchcnl.g:404:1: ( ruleOrObjectConceptExpression EOF )
            // InternalArchcnl.g:405:1: ruleOrObjectConceptExpression EOF
            {
             before(grammarAccess.getOrObjectConceptExpressionRule()); 
            pushFollow(FOLLOW_1);
            ruleOrObjectConceptExpression();

            state._fsp--;

             after(grammarAccess.getOrObjectConceptExpressionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleOrObjectConceptExpression"


    // $ANTLR start "ruleOrObjectConceptExpression"
    // InternalArchcnl.g:412:1: ruleOrObjectConceptExpression : ( ( rule__OrObjectConceptExpression__Group__0 ) ) ;
    public final void ruleOrObjectConceptExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:416:2: ( ( ( rule__OrObjectConceptExpression__Group__0 ) ) )
            // InternalArchcnl.g:417:2: ( ( rule__OrObjectConceptExpression__Group__0 ) )
            {
            // InternalArchcnl.g:417:2: ( ( rule__OrObjectConceptExpression__Group__0 ) )
            // InternalArchcnl.g:418:3: ( rule__OrObjectConceptExpression__Group__0 )
            {
             before(grammarAccess.getOrObjectConceptExpressionAccess().getGroup()); 
            // InternalArchcnl.g:419:3: ( rule__OrObjectConceptExpression__Group__0 )
            // InternalArchcnl.g:419:4: rule__OrObjectConceptExpression__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__OrObjectConceptExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getOrObjectConceptExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleOrObjectConceptExpression"


    // $ANTLR start "entryRuleRelation"
    // InternalArchcnl.g:428:1: entryRuleRelation : ruleRelation EOF ;
    public final void entryRuleRelation() throws RecognitionException {
        try {
            // InternalArchcnl.g:429:1: ( ruleRelation EOF )
            // InternalArchcnl.g:430:1: ruleRelation EOF
            {
             before(grammarAccess.getRelationRule()); 
            pushFollow(FOLLOW_1);
            ruleRelation();

            state._fsp--;

             after(grammarAccess.getRelationRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRelation"


    // $ANTLR start "ruleRelation"
    // InternalArchcnl.g:437:1: ruleRelation : ( ( rule__Relation__Alternatives ) ) ;
    public final void ruleRelation() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:441:2: ( ( ( rule__Relation__Alternatives ) ) )
            // InternalArchcnl.g:442:2: ( ( rule__Relation__Alternatives ) )
            {
            // InternalArchcnl.g:442:2: ( ( rule__Relation__Alternatives ) )
            // InternalArchcnl.g:443:3: ( rule__Relation__Alternatives )
            {
             before(grammarAccess.getRelationAccess().getAlternatives()); 
            // InternalArchcnl.g:444:3: ( rule__Relation__Alternatives )
            // InternalArchcnl.g:444:4: rule__Relation__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Relation__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getRelationAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRelation"


    // $ANTLR start "entryRuleDatatypeRelation"
    // InternalArchcnl.g:453:1: entryRuleDatatypeRelation : ruleDatatypeRelation EOF ;
    public final void entryRuleDatatypeRelation() throws RecognitionException {
        try {
            // InternalArchcnl.g:454:1: ( ruleDatatypeRelation EOF )
            // InternalArchcnl.g:455:1: ruleDatatypeRelation EOF
            {
             before(grammarAccess.getDatatypeRelationRule()); 
            pushFollow(FOLLOW_1);
            ruleDatatypeRelation();

            state._fsp--;

             after(grammarAccess.getDatatypeRelationRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDatatypeRelation"


    // $ANTLR start "ruleDatatypeRelation"
    // InternalArchcnl.g:462:1: ruleDatatypeRelation : ( ( rule__DatatypeRelation__Group__0 ) ) ;
    public final void ruleDatatypeRelation() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:466:2: ( ( ( rule__DatatypeRelation__Group__0 ) ) )
            // InternalArchcnl.g:467:2: ( ( rule__DatatypeRelation__Group__0 ) )
            {
            // InternalArchcnl.g:467:2: ( ( rule__DatatypeRelation__Group__0 ) )
            // InternalArchcnl.g:468:3: ( rule__DatatypeRelation__Group__0 )
            {
             before(grammarAccess.getDatatypeRelationAccess().getGroup()); 
            // InternalArchcnl.g:469:3: ( rule__DatatypeRelation__Group__0 )
            // InternalArchcnl.g:469:4: rule__DatatypeRelation__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DatatypeRelation__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDatatypeRelationAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDatatypeRelation"


    // $ANTLR start "entryRuleObjectRelation"
    // InternalArchcnl.g:478:1: entryRuleObjectRelation : ruleObjectRelation EOF ;
    public final void entryRuleObjectRelation() throws RecognitionException {
        try {
            // InternalArchcnl.g:479:1: ( ruleObjectRelation EOF )
            // InternalArchcnl.g:480:1: ruleObjectRelation EOF
            {
             before(grammarAccess.getObjectRelationRule()); 
            pushFollow(FOLLOW_1);
            ruleObjectRelation();

            state._fsp--;

             after(grammarAccess.getObjectRelationRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleObjectRelation"


    // $ANTLR start "ruleObjectRelation"
    // InternalArchcnl.g:487:1: ruleObjectRelation : ( ( rule__ObjectRelation__RelationNameAssignment ) ) ;
    public final void ruleObjectRelation() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:491:2: ( ( ( rule__ObjectRelation__RelationNameAssignment ) ) )
            // InternalArchcnl.g:492:2: ( ( rule__ObjectRelation__RelationNameAssignment ) )
            {
            // InternalArchcnl.g:492:2: ( ( rule__ObjectRelation__RelationNameAssignment ) )
            // InternalArchcnl.g:493:3: ( rule__ObjectRelation__RelationNameAssignment )
            {
             before(grammarAccess.getObjectRelationAccess().getRelationNameAssignment()); 
            // InternalArchcnl.g:494:3: ( rule__ObjectRelation__RelationNameAssignment )
            // InternalArchcnl.g:494:4: rule__ObjectRelation__RelationNameAssignment
            {
            pushFollow(FOLLOW_2);
            rule__ObjectRelation__RelationNameAssignment();

            state._fsp--;


            }

             after(grammarAccess.getObjectRelationAccess().getRelationNameAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleObjectRelation"


    // $ANTLR start "entryRuleConceptExpression"
    // InternalArchcnl.g:503:1: entryRuleConceptExpression : ruleConceptExpression EOF ;
    public final void entryRuleConceptExpression() throws RecognitionException {
        try {
            // InternalArchcnl.g:504:1: ( ruleConceptExpression EOF )
            // InternalArchcnl.g:505:1: ruleConceptExpression EOF
            {
             before(grammarAccess.getConceptExpressionRule()); 
            pushFollow(FOLLOW_1);
            ruleConceptExpression();

            state._fsp--;

             after(grammarAccess.getConceptExpressionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleConceptExpression"


    // $ANTLR start "ruleConceptExpression"
    // InternalArchcnl.g:512:1: ruleConceptExpression : ( ( rule__ConceptExpression__Group__0 ) ) ;
    public final void ruleConceptExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:516:2: ( ( ( rule__ConceptExpression__Group__0 ) ) )
            // InternalArchcnl.g:517:2: ( ( rule__ConceptExpression__Group__0 ) )
            {
            // InternalArchcnl.g:517:2: ( ( rule__ConceptExpression__Group__0 ) )
            // InternalArchcnl.g:518:3: ( rule__ConceptExpression__Group__0 )
            {
             before(grammarAccess.getConceptExpressionAccess().getGroup()); 
            // InternalArchcnl.g:519:3: ( rule__ConceptExpression__Group__0 )
            // InternalArchcnl.g:519:4: rule__ConceptExpression__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ConceptExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getConceptExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleConceptExpression"


    // $ANTLR start "entryRuleThatExpression"
    // InternalArchcnl.g:528:1: entryRuleThatExpression : ruleThatExpression EOF ;
    public final void entryRuleThatExpression() throws RecognitionException {
        try {
            // InternalArchcnl.g:529:1: ( ruleThatExpression EOF )
            // InternalArchcnl.g:530:1: ruleThatExpression EOF
            {
             before(grammarAccess.getThatExpressionRule()); 
            pushFollow(FOLLOW_1);
            ruleThatExpression();

            state._fsp--;

             after(grammarAccess.getThatExpressionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleThatExpression"


    // $ANTLR start "ruleThatExpression"
    // InternalArchcnl.g:537:1: ruleThatExpression : ( ( rule__ThatExpression__Group__0 ) ) ;
    public final void ruleThatExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:541:2: ( ( ( rule__ThatExpression__Group__0 ) ) )
            // InternalArchcnl.g:542:2: ( ( rule__ThatExpression__Group__0 ) )
            {
            // InternalArchcnl.g:542:2: ( ( rule__ThatExpression__Group__0 ) )
            // InternalArchcnl.g:543:3: ( rule__ThatExpression__Group__0 )
            {
             before(grammarAccess.getThatExpressionAccess().getGroup()); 
            // InternalArchcnl.g:544:3: ( rule__ThatExpression__Group__0 )
            // InternalArchcnl.g:544:4: rule__ThatExpression__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ThatExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getThatExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleThatExpression"


    // $ANTLR start "entryRuleStatementList"
    // InternalArchcnl.g:553:1: entryRuleStatementList : ruleStatementList EOF ;
    public final void entryRuleStatementList() throws RecognitionException {
        try {
            // InternalArchcnl.g:554:1: ( ruleStatementList EOF )
            // InternalArchcnl.g:555:1: ruleStatementList EOF
            {
             before(grammarAccess.getStatementListRule()); 
            pushFollow(FOLLOW_1);
            ruleStatementList();

            state._fsp--;

             after(grammarAccess.getStatementListRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStatementList"


    // $ANTLR start "ruleStatementList"
    // InternalArchcnl.g:562:1: ruleStatementList : ( ( rule__StatementList__Group__0 ) ) ;
    public final void ruleStatementList() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:566:2: ( ( ( rule__StatementList__Group__0 ) ) )
            // InternalArchcnl.g:567:2: ( ( rule__StatementList__Group__0 ) )
            {
            // InternalArchcnl.g:567:2: ( ( rule__StatementList__Group__0 ) )
            // InternalArchcnl.g:568:3: ( rule__StatementList__Group__0 )
            {
             before(grammarAccess.getStatementListAccess().getGroup()); 
            // InternalArchcnl.g:569:3: ( rule__StatementList__Group__0 )
            // InternalArchcnl.g:569:4: rule__StatementList__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__StatementList__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getStatementListAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStatementList"


    // $ANTLR start "entryRuleVariableStatement"
    // InternalArchcnl.g:578:1: entryRuleVariableStatement : ruleVariableStatement EOF ;
    public final void entryRuleVariableStatement() throws RecognitionException {
        try {
            // InternalArchcnl.g:579:1: ( ruleVariableStatement EOF )
            // InternalArchcnl.g:580:1: ruleVariableStatement EOF
            {
             before(grammarAccess.getVariableStatementRule()); 
            pushFollow(FOLLOW_1);
            ruleVariableStatement();

            state._fsp--;

             after(grammarAccess.getVariableStatementRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVariableStatement"


    // $ANTLR start "ruleVariableStatement"
    // InternalArchcnl.g:587:1: ruleVariableStatement : ( ( rule__VariableStatement__Group__0 ) ) ;
    public final void ruleVariableStatement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:591:2: ( ( ( rule__VariableStatement__Group__0 ) ) )
            // InternalArchcnl.g:592:2: ( ( rule__VariableStatement__Group__0 ) )
            {
            // InternalArchcnl.g:592:2: ( ( rule__VariableStatement__Group__0 ) )
            // InternalArchcnl.g:593:3: ( rule__VariableStatement__Group__0 )
            {
             before(grammarAccess.getVariableStatementAccess().getGroup()); 
            // InternalArchcnl.g:594:3: ( rule__VariableStatement__Group__0 )
            // InternalArchcnl.g:594:4: rule__VariableStatement__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__VariableStatement__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getVariableStatementAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVariableStatement"


    // $ANTLR start "entryRuleDataStatement"
    // InternalArchcnl.g:603:1: entryRuleDataStatement : ruleDataStatement EOF ;
    public final void entryRuleDataStatement() throws RecognitionException {
        try {
            // InternalArchcnl.g:604:1: ( ruleDataStatement EOF )
            // InternalArchcnl.g:605:1: ruleDataStatement EOF
            {
             before(grammarAccess.getDataStatementRule()); 
            pushFollow(FOLLOW_1);
            ruleDataStatement();

            state._fsp--;

             after(grammarAccess.getDataStatementRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDataStatement"


    // $ANTLR start "ruleDataStatement"
    // InternalArchcnl.g:612:1: ruleDataStatement : ( ( rule__DataStatement__Alternatives ) ) ;
    public final void ruleDataStatement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:616:2: ( ( ( rule__DataStatement__Alternatives ) ) )
            // InternalArchcnl.g:617:2: ( ( rule__DataStatement__Alternatives ) )
            {
            // InternalArchcnl.g:617:2: ( ( rule__DataStatement__Alternatives ) )
            // InternalArchcnl.g:618:3: ( rule__DataStatement__Alternatives )
            {
             before(grammarAccess.getDataStatementAccess().getAlternatives()); 
            // InternalArchcnl.g:619:3: ( rule__DataStatement__Alternatives )
            // InternalArchcnl.g:619:4: rule__DataStatement__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__DataStatement__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getDataStatementAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDataStatement"


    // $ANTLR start "entryRuleFactStatement"
    // InternalArchcnl.g:628:1: entryRuleFactStatement : ruleFactStatement EOF ;
    public final void entryRuleFactStatement() throws RecognitionException {
        try {
            // InternalArchcnl.g:629:1: ( ruleFactStatement EOF )
            // InternalArchcnl.g:630:1: ruleFactStatement EOF
            {
             before(grammarAccess.getFactStatementRule()); 
            pushFollow(FOLLOW_1);
            ruleFactStatement();

            state._fsp--;

             after(grammarAccess.getFactStatementRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleFactStatement"


    // $ANTLR start "ruleFactStatement"
    // InternalArchcnl.g:637:1: ruleFactStatement : ( ( rule__FactStatement__Group__0 ) ) ;
    public final void ruleFactStatement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:641:2: ( ( ( rule__FactStatement__Group__0 ) ) )
            // InternalArchcnl.g:642:2: ( ( rule__FactStatement__Group__0 ) )
            {
            // InternalArchcnl.g:642:2: ( ( rule__FactStatement__Group__0 ) )
            // InternalArchcnl.g:643:3: ( rule__FactStatement__Group__0 )
            {
             before(grammarAccess.getFactStatementAccess().getGroup()); 
            // InternalArchcnl.g:644:3: ( rule__FactStatement__Group__0 )
            // InternalArchcnl.g:644:4: rule__FactStatement__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__FactStatement__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getFactStatementAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleFactStatement"


    // $ANTLR start "entryRuleConceptAssertion"
    // InternalArchcnl.g:653:1: entryRuleConceptAssertion : ruleConceptAssertion EOF ;
    public final void entryRuleConceptAssertion() throws RecognitionException {
        try {
            // InternalArchcnl.g:654:1: ( ruleConceptAssertion EOF )
            // InternalArchcnl.g:655:1: ruleConceptAssertion EOF
            {
             before(grammarAccess.getConceptAssertionRule()); 
            pushFollow(FOLLOW_1);
            ruleConceptAssertion();

            state._fsp--;

             after(grammarAccess.getConceptAssertionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleConceptAssertion"


    // $ANTLR start "ruleConceptAssertion"
    // InternalArchcnl.g:662:1: ruleConceptAssertion : ( ( rule__ConceptAssertion__Group__0 ) ) ;
    public final void ruleConceptAssertion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:666:2: ( ( ( rule__ConceptAssertion__Group__0 ) ) )
            // InternalArchcnl.g:667:2: ( ( rule__ConceptAssertion__Group__0 ) )
            {
            // InternalArchcnl.g:667:2: ( ( rule__ConceptAssertion__Group__0 ) )
            // InternalArchcnl.g:668:3: ( rule__ConceptAssertion__Group__0 )
            {
             before(grammarAccess.getConceptAssertionAccess().getGroup()); 
            // InternalArchcnl.g:669:3: ( rule__ConceptAssertion__Group__0 )
            // InternalArchcnl.g:669:4: rule__ConceptAssertion__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ConceptAssertion__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getConceptAssertionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleConceptAssertion"


    // $ANTLR start "entryRuleRoleAssertion"
    // InternalArchcnl.g:678:1: entryRuleRoleAssertion : ruleRoleAssertion EOF ;
    public final void entryRuleRoleAssertion() throws RecognitionException {
        try {
            // InternalArchcnl.g:679:1: ( ruleRoleAssertion EOF )
            // InternalArchcnl.g:680:1: ruleRoleAssertion EOF
            {
             before(grammarAccess.getRoleAssertionRule()); 
            pushFollow(FOLLOW_1);
            ruleRoleAssertion();

            state._fsp--;

             after(grammarAccess.getRoleAssertionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRoleAssertion"


    // $ANTLR start "ruleRoleAssertion"
    // InternalArchcnl.g:687:1: ruleRoleAssertion : ( ( rule__RoleAssertion__Alternatives ) ) ;
    public final void ruleRoleAssertion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:691:2: ( ( ( rule__RoleAssertion__Alternatives ) ) )
            // InternalArchcnl.g:692:2: ( ( rule__RoleAssertion__Alternatives ) )
            {
            // InternalArchcnl.g:692:2: ( ( rule__RoleAssertion__Alternatives ) )
            // InternalArchcnl.g:693:3: ( rule__RoleAssertion__Alternatives )
            {
             before(grammarAccess.getRoleAssertionAccess().getAlternatives()); 
            // InternalArchcnl.g:694:3: ( rule__RoleAssertion__Alternatives )
            // InternalArchcnl.g:694:4: rule__RoleAssertion__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__RoleAssertion__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getRoleAssertionAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRoleAssertion"


    // $ANTLR start "entryRuleDatatypePropertyAssertion"
    // InternalArchcnl.g:703:1: entryRuleDatatypePropertyAssertion : ruleDatatypePropertyAssertion EOF ;
    public final void entryRuleDatatypePropertyAssertion() throws RecognitionException {
        try {
            // InternalArchcnl.g:704:1: ( ruleDatatypePropertyAssertion EOF )
            // InternalArchcnl.g:705:1: ruleDatatypePropertyAssertion EOF
            {
             before(grammarAccess.getDatatypePropertyAssertionRule()); 
            pushFollow(FOLLOW_1);
            ruleDatatypePropertyAssertion();

            state._fsp--;

             after(grammarAccess.getDatatypePropertyAssertionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDatatypePropertyAssertion"


    // $ANTLR start "ruleDatatypePropertyAssertion"
    // InternalArchcnl.g:712:1: ruleDatatypePropertyAssertion : ( ( rule__DatatypePropertyAssertion__Group__0 ) ) ;
    public final void ruleDatatypePropertyAssertion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:716:2: ( ( ( rule__DatatypePropertyAssertion__Group__0 ) ) )
            // InternalArchcnl.g:717:2: ( ( rule__DatatypePropertyAssertion__Group__0 ) )
            {
            // InternalArchcnl.g:717:2: ( ( rule__DatatypePropertyAssertion__Group__0 ) )
            // InternalArchcnl.g:718:3: ( rule__DatatypePropertyAssertion__Group__0 )
            {
             before(grammarAccess.getDatatypePropertyAssertionAccess().getGroup()); 
            // InternalArchcnl.g:719:3: ( rule__DatatypePropertyAssertion__Group__0 )
            // InternalArchcnl.g:719:4: rule__DatatypePropertyAssertion__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DatatypePropertyAssertion__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDatatypePropertyAssertionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDatatypePropertyAssertion"


    // $ANTLR start "entryRuleObjectPropertyAssertion"
    // InternalArchcnl.g:728:1: entryRuleObjectPropertyAssertion : ruleObjectPropertyAssertion EOF ;
    public final void entryRuleObjectPropertyAssertion() throws RecognitionException {
        try {
            // InternalArchcnl.g:729:1: ( ruleObjectPropertyAssertion EOF )
            // InternalArchcnl.g:730:1: ruleObjectPropertyAssertion EOF
            {
             before(grammarAccess.getObjectPropertyAssertionRule()); 
            pushFollow(FOLLOW_1);
            ruleObjectPropertyAssertion();

            state._fsp--;

             after(grammarAccess.getObjectPropertyAssertionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleObjectPropertyAssertion"


    // $ANTLR start "ruleObjectPropertyAssertion"
    // InternalArchcnl.g:737:1: ruleObjectPropertyAssertion : ( ( rule__ObjectPropertyAssertion__Group__0 ) ) ;
    public final void ruleObjectPropertyAssertion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:741:2: ( ( ( rule__ObjectPropertyAssertion__Group__0 ) ) )
            // InternalArchcnl.g:742:2: ( ( rule__ObjectPropertyAssertion__Group__0 ) )
            {
            // InternalArchcnl.g:742:2: ( ( rule__ObjectPropertyAssertion__Group__0 ) )
            // InternalArchcnl.g:743:3: ( rule__ObjectPropertyAssertion__Group__0 )
            {
             before(grammarAccess.getObjectPropertyAssertionAccess().getGroup()); 
            // InternalArchcnl.g:744:3: ( rule__ObjectPropertyAssertion__Group__0 )
            // InternalArchcnl.g:744:4: rule__ObjectPropertyAssertion__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ObjectPropertyAssertion__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getObjectPropertyAssertionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleObjectPropertyAssertion"


    // $ANTLR start "entryRuleConcept"
    // InternalArchcnl.g:753:1: entryRuleConcept : ruleConcept EOF ;
    public final void entryRuleConcept() throws RecognitionException {
        try {
            // InternalArchcnl.g:754:1: ( ruleConcept EOF )
            // InternalArchcnl.g:755:1: ruleConcept EOF
            {
             before(grammarAccess.getConceptRule()); 
            pushFollow(FOLLOW_1);
            ruleConcept();

            state._fsp--;

             after(grammarAccess.getConceptRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleConcept"


    // $ANTLR start "ruleConcept"
    // InternalArchcnl.g:762:1: ruleConcept : ( ( rule__Concept__ConceptNameAssignment ) ) ;
    public final void ruleConcept() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:766:2: ( ( ( rule__Concept__ConceptNameAssignment ) ) )
            // InternalArchcnl.g:767:2: ( ( rule__Concept__ConceptNameAssignment ) )
            {
            // InternalArchcnl.g:767:2: ( ( rule__Concept__ConceptNameAssignment ) )
            // InternalArchcnl.g:768:3: ( rule__Concept__ConceptNameAssignment )
            {
             before(grammarAccess.getConceptAccess().getConceptNameAssignment()); 
            // InternalArchcnl.g:769:3: ( rule__Concept__ConceptNameAssignment )
            // InternalArchcnl.g:769:4: rule__Concept__ConceptNameAssignment
            {
            pushFollow(FOLLOW_2);
            rule__Concept__ConceptNameAssignment();

            state._fsp--;


            }

             after(grammarAccess.getConceptAccess().getConceptNameAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleConcept"


    // $ANTLR start "entryRuleVariable"
    // InternalArchcnl.g:778:1: entryRuleVariable : ruleVariable EOF ;
    public final void entryRuleVariable() throws RecognitionException {
        try {
            // InternalArchcnl.g:779:1: ( ruleVariable EOF )
            // InternalArchcnl.g:780:1: ruleVariable EOF
            {
             before(grammarAccess.getVariableRule()); 
            pushFollow(FOLLOW_1);
            ruleVariable();

            state._fsp--;

             after(grammarAccess.getVariableRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVariable"


    // $ANTLR start "ruleVariable"
    // InternalArchcnl.g:787:1: ruleVariable : ( ( rule__Variable__NameAssignment ) ) ;
    public final void ruleVariable() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:791:2: ( ( ( rule__Variable__NameAssignment ) ) )
            // InternalArchcnl.g:792:2: ( ( rule__Variable__NameAssignment ) )
            {
            // InternalArchcnl.g:792:2: ( ( rule__Variable__NameAssignment ) )
            // InternalArchcnl.g:793:3: ( rule__Variable__NameAssignment )
            {
             before(grammarAccess.getVariableAccess().getNameAssignment()); 
            // InternalArchcnl.g:794:3: ( rule__Variable__NameAssignment )
            // InternalArchcnl.g:794:4: rule__Variable__NameAssignment
            {
            pushFollow(FOLLOW_2);
            rule__Variable__NameAssignment();

            state._fsp--;


            }

             after(grammarAccess.getVariableAccess().getNameAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVariable"


    // $ANTLR start "rule__Sentence__Alternatives"
    // InternalArchcnl.g:802:1: rule__Sentence__Alternatives : ( ( ( rule__Sentence__Group_0__0 ) ) | ( ( rule__Sentence__RuletypeAssignment_1 ) ) | ( ( rule__Sentence__FactsAssignment_2 ) ) );
    public final void rule__Sentence__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:806:1: ( ( ( rule__Sentence__Group_0__0 ) ) | ( ( rule__Sentence__RuletypeAssignment_1 ) ) | ( ( rule__Sentence__FactsAssignment_2 ) ) )
            int alt2=3;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt2=1;
                }
                break;
            case 21:
            case 24:
            case 38:
            case 39:
                {
                alt2=2;
                }
                break;
            case RULE_NAME:
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
                    // InternalArchcnl.g:807:2: ( ( rule__Sentence__Group_0__0 ) )
                    {
                    // InternalArchcnl.g:807:2: ( ( rule__Sentence__Group_0__0 ) )
                    // InternalArchcnl.g:808:3: ( rule__Sentence__Group_0__0 )
                    {
                     before(grammarAccess.getSentenceAccess().getGroup_0()); 
                    // InternalArchcnl.g:809:3: ( rule__Sentence__Group_0__0 )
                    // InternalArchcnl.g:809:4: rule__Sentence__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Sentence__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getSentenceAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:813:2: ( ( rule__Sentence__RuletypeAssignment_1 ) )
                    {
                    // InternalArchcnl.g:813:2: ( ( rule__Sentence__RuletypeAssignment_1 ) )
                    // InternalArchcnl.g:814:3: ( rule__Sentence__RuletypeAssignment_1 )
                    {
                     before(grammarAccess.getSentenceAccess().getRuletypeAssignment_1()); 
                    // InternalArchcnl.g:815:3: ( rule__Sentence__RuletypeAssignment_1 )
                    // InternalArchcnl.g:815:4: rule__Sentence__RuletypeAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Sentence__RuletypeAssignment_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getSentenceAccess().getRuletypeAssignment_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalArchcnl.g:819:2: ( ( rule__Sentence__FactsAssignment_2 ) )
                    {
                    // InternalArchcnl.g:819:2: ( ( rule__Sentence__FactsAssignment_2 ) )
                    // InternalArchcnl.g:820:3: ( rule__Sentence__FactsAssignment_2 )
                    {
                     before(grammarAccess.getSentenceAccess().getFactsAssignment_2()); 
                    // InternalArchcnl.g:821:3: ( rule__Sentence__FactsAssignment_2 )
                    // InternalArchcnl.g:821:4: rule__Sentence__FactsAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Sentence__FactsAssignment_2();

                    state._fsp--;


                    }

                     after(grammarAccess.getSentenceAccess().getFactsAssignment_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__Alternatives"


    // $ANTLR start "rule__Sentence__RuletypeAlternatives_0_2_0"
    // InternalArchcnl.g:829:1: rule__Sentence__RuletypeAlternatives_0_2_0 : ( ( ruleMustRuleType ) | ( ruleCanOnlyRuleType ) | ( ruleCardinalityRuleType ) | ( ruleSubConceptRuleType ) );
    public final void rule__Sentence__RuletypeAlternatives_0_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:833:1: ( ( ruleMustRuleType ) | ( ruleCanOnlyRuleType ) | ( ruleCardinalityRuleType ) | ( ruleSubConceptRuleType ) )
            int alt3=4;
            switch ( input.LA(1) ) {
            case 28:
                {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==RULE_RELATION_NAME) ) {
                    alt3=1;
                }
                else if ( (LA3_1==30) ) {
                    alt3=4;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
                }
                break;
            case 40:
                {
                alt3=2;
                }
                break;
            case 22:
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
                    // InternalArchcnl.g:834:2: ( ruleMustRuleType )
                    {
                    // InternalArchcnl.g:834:2: ( ruleMustRuleType )
                    // InternalArchcnl.g:835:3: ruleMustRuleType
                    {
                     before(grammarAccess.getSentenceAccess().getRuletypeMustRuleTypeParserRuleCall_0_2_0_0()); 
                    pushFollow(FOLLOW_2);
                    ruleMustRuleType();

                    state._fsp--;

                     after(grammarAccess.getSentenceAccess().getRuletypeMustRuleTypeParserRuleCall_0_2_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:840:2: ( ruleCanOnlyRuleType )
                    {
                    // InternalArchcnl.g:840:2: ( ruleCanOnlyRuleType )
                    // InternalArchcnl.g:841:3: ruleCanOnlyRuleType
                    {
                     before(grammarAccess.getSentenceAccess().getRuletypeCanOnlyRuleTypeParserRuleCall_0_2_0_1()); 
                    pushFollow(FOLLOW_2);
                    ruleCanOnlyRuleType();

                    state._fsp--;

                     after(grammarAccess.getSentenceAccess().getRuletypeCanOnlyRuleTypeParserRuleCall_0_2_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalArchcnl.g:846:2: ( ruleCardinalityRuleType )
                    {
                    // InternalArchcnl.g:846:2: ( ruleCardinalityRuleType )
                    // InternalArchcnl.g:847:3: ruleCardinalityRuleType
                    {
                     before(grammarAccess.getSentenceAccess().getRuletypeCardinalityRuleTypeParserRuleCall_0_2_0_2()); 
                    pushFollow(FOLLOW_2);
                    ruleCardinalityRuleType();

                    state._fsp--;

                     after(grammarAccess.getSentenceAccess().getRuletypeCardinalityRuleTypeParserRuleCall_0_2_0_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalArchcnl.g:852:2: ( ruleSubConceptRuleType )
                    {
                    // InternalArchcnl.g:852:2: ( ruleSubConceptRuleType )
                    // InternalArchcnl.g:853:3: ruleSubConceptRuleType
                    {
                     before(grammarAccess.getSentenceAccess().getRuletypeSubConceptRuleTypeParserRuleCall_0_2_0_3()); 
                    pushFollow(FOLLOW_2);
                    ruleSubConceptRuleType();

                    state._fsp--;

                     after(grammarAccess.getSentenceAccess().getRuletypeSubConceptRuleTypeParserRuleCall_0_2_0_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__RuletypeAlternatives_0_2_0"


    // $ANTLR start "rule__Sentence__RuletypeAlternatives_1_0"
    // InternalArchcnl.g:862:1: rule__Sentence__RuletypeAlternatives_1_0 : ( ( ruleOnlyCanRuleType ) | ( ruleConditionalRuleType ) | ( ruleNegationRuleType ) );
    public final void rule__Sentence__RuletypeAlternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:866:1: ( ( ruleOnlyCanRuleType ) | ( ruleConditionalRuleType ) | ( ruleNegationRuleType ) )
            int alt4=3;
            switch ( input.LA(1) ) {
            case 39:
                {
                alt4=1;
                }
                break;
            case 38:
                {
                alt4=2;
                }
                break;
            case 21:
            case 24:
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
                    // InternalArchcnl.g:867:2: ( ruleOnlyCanRuleType )
                    {
                    // InternalArchcnl.g:867:2: ( ruleOnlyCanRuleType )
                    // InternalArchcnl.g:868:3: ruleOnlyCanRuleType
                    {
                     before(grammarAccess.getSentenceAccess().getRuletypeOnlyCanRuleTypeParserRuleCall_1_0_0()); 
                    pushFollow(FOLLOW_2);
                    ruleOnlyCanRuleType();

                    state._fsp--;

                     after(grammarAccess.getSentenceAccess().getRuletypeOnlyCanRuleTypeParserRuleCall_1_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:873:2: ( ruleConditionalRuleType )
                    {
                    // InternalArchcnl.g:873:2: ( ruleConditionalRuleType )
                    // InternalArchcnl.g:874:3: ruleConditionalRuleType
                    {
                     before(grammarAccess.getSentenceAccess().getRuletypeConditionalRuleTypeParserRuleCall_1_0_1()); 
                    pushFollow(FOLLOW_2);
                    ruleConditionalRuleType();

                    state._fsp--;

                     after(grammarAccess.getSentenceAccess().getRuletypeConditionalRuleTypeParserRuleCall_1_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalArchcnl.g:879:2: ( ruleNegationRuleType )
                    {
                    // InternalArchcnl.g:879:2: ( ruleNegationRuleType )
                    // InternalArchcnl.g:880:3: ruleNegationRuleType
                    {
                     before(grammarAccess.getSentenceAccess().getRuletypeNegationRuleTypeParserRuleCall_1_0_2()); 
                    pushFollow(FOLLOW_2);
                    ruleNegationRuleType();

                    state._fsp--;

                     after(grammarAccess.getSentenceAccess().getRuletypeNegationRuleTypeParserRuleCall_1_0_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__RuletypeAlternatives_1_0"


    // $ANTLR start "rule__NegationRuleType__Alternatives"
    // InternalArchcnl.g:889:1: rule__NegationRuleType__Alternatives : ( ( ruleNothing ) | ( ( rule__NegationRuleType__Group_1__0 ) ) );
    public final void rule__NegationRuleType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:893:1: ( ( ruleNothing ) | ( ( rule__NegationRuleType__Group_1__0 ) ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==24) ) {
                alt5=1;
            }
            else if ( (LA5_0==21) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalArchcnl.g:894:2: ( ruleNothing )
                    {
                    // InternalArchcnl.g:894:2: ( ruleNothing )
                    // InternalArchcnl.g:895:3: ruleNothing
                    {
                     before(grammarAccess.getNegationRuleTypeAccess().getNothingParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleNothing();

                    state._fsp--;

                     after(grammarAccess.getNegationRuleTypeAccess().getNothingParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:900:2: ( ( rule__NegationRuleType__Group_1__0 ) )
                    {
                    // InternalArchcnl.g:900:2: ( ( rule__NegationRuleType__Group_1__0 ) )
                    // InternalArchcnl.g:901:3: ( rule__NegationRuleType__Group_1__0 )
                    {
                     before(grammarAccess.getNegationRuleTypeAccess().getGroup_1()); 
                    // InternalArchcnl.g:902:3: ( rule__NegationRuleType__Group_1__0 )
                    // InternalArchcnl.g:902:4: rule__NegationRuleType__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__NegationRuleType__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getNegationRuleTypeAccess().getGroup_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Alternatives"


    // $ANTLR start "rule__Object__Alternatives"
    // InternalArchcnl.g:910:1: rule__Object__Alternatives : ( ( ( rule__Object__AnythingAssignment_0 ) ) | ( ( rule__Object__Group_1__0 ) ) );
    public final void rule__Object__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:914:1: ( ( ( rule__Object__AnythingAssignment_0 ) ) | ( ( rule__Object__Group_1__0 ) ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_RELATION_NAME) ) {
                switch ( input.LA(2) ) {
                case 33:
                    {
                    int LA6_2 = input.LA(3);

                    if ( (LA6_2==23) ) {
                        alt6=1;
                    }
                    else if ( (LA6_2==RULE_INT||(LA6_2>=RULE_STRING && LA6_2<=RULE_NAME)||(LA6_2>=14 && LA6_2<=18)) ) {
                        alt6=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 2, input);

                        throw nvae;
                    }
                    }
                    break;
                case 23:
                    {
                    alt6=1;
                    }
                    break;
                case RULE_NAME:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                    {
                    alt6=2;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalArchcnl.g:915:2: ( ( rule__Object__AnythingAssignment_0 ) )
                    {
                    // InternalArchcnl.g:915:2: ( ( rule__Object__AnythingAssignment_0 ) )
                    // InternalArchcnl.g:916:3: ( rule__Object__AnythingAssignment_0 )
                    {
                     before(grammarAccess.getObjectAccess().getAnythingAssignment_0()); 
                    // InternalArchcnl.g:917:3: ( rule__Object__AnythingAssignment_0 )
                    // InternalArchcnl.g:917:4: rule__Object__AnythingAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Object__AnythingAssignment_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getObjectAccess().getAnythingAssignment_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:921:2: ( ( rule__Object__Group_1__0 ) )
                    {
                    // InternalArchcnl.g:921:2: ( ( rule__Object__Group_1__0 ) )
                    // InternalArchcnl.g:922:3: ( rule__Object__Group_1__0 )
                    {
                     before(grammarAccess.getObjectAccess().getGroup_1()); 
                    // InternalArchcnl.g:923:3: ( rule__Object__Group_1__0 )
                    // InternalArchcnl.g:923:4: rule__Object__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Object__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getObjectAccess().getGroup_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Object__Alternatives"


    // $ANTLR start "rule__Object__Alternatives_1_1"
    // InternalArchcnl.g:931:1: rule__Object__Alternatives_1_1 : ( ( ( rule__Object__ObjectAndListAssignment_1_1_0 ) ) | ( ( rule__Object__ObjectOrListAssignment_1_1_1 ) ) );
    public final void rule__Object__Alternatives_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:935:1: ( ( ( rule__Object__ObjectAndListAssignment_1_1_0 ) ) | ( ( rule__Object__ObjectOrListAssignment_1_1_1 ) ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==31) ) {
                alt7=1;
            }
            else if ( (LA7_0==32) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // InternalArchcnl.g:936:2: ( ( rule__Object__ObjectAndListAssignment_1_1_0 ) )
                    {
                    // InternalArchcnl.g:936:2: ( ( rule__Object__ObjectAndListAssignment_1_1_0 ) )
                    // InternalArchcnl.g:937:3: ( rule__Object__ObjectAndListAssignment_1_1_0 )
                    {
                     before(grammarAccess.getObjectAccess().getObjectAndListAssignment_1_1_0()); 
                    // InternalArchcnl.g:938:3: ( rule__Object__ObjectAndListAssignment_1_1_0 )
                    // InternalArchcnl.g:938:4: rule__Object__ObjectAndListAssignment_1_1_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Object__ObjectAndListAssignment_1_1_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getObjectAccess().getObjectAndListAssignment_1_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:942:2: ( ( rule__Object__ObjectOrListAssignment_1_1_1 ) )
                    {
                    // InternalArchcnl.g:942:2: ( ( rule__Object__ObjectOrListAssignment_1_1_1 ) )
                    // InternalArchcnl.g:943:3: ( rule__Object__ObjectOrListAssignment_1_1_1 )
                    {
                     before(grammarAccess.getObjectAccess().getObjectOrListAssignment_1_1_1()); 
                    // InternalArchcnl.g:944:3: ( rule__Object__ObjectOrListAssignment_1_1_1 )
                    // InternalArchcnl.g:944:4: rule__Object__ObjectOrListAssignment_1_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Object__ObjectOrListAssignment_1_1_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getObjectAccess().getObjectOrListAssignment_1_1_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Object__Alternatives_1_1"


    // $ANTLR start "rule__ObjectConceptExpression__Alternatives"
    // InternalArchcnl.g:952:1: rule__ObjectConceptExpression__Alternatives : ( ( ( rule__ObjectConceptExpression__Group_0__0 ) ) | ( ( rule__ObjectConceptExpression__Group_1__0 ) ) );
    public final void rule__ObjectConceptExpression__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:956:1: ( ( ( rule__ObjectConceptExpression__Group_0__0 ) ) | ( ( rule__ObjectConceptExpression__Group_1__0 ) ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_RELATION_NAME) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==33) ) {
                    int LA8_2 = input.LA(3);

                    if ( (LA8_2==RULE_NAME||(LA8_2>=14 && LA8_2<=18)) ) {
                        alt8=1;
                    }
                    else if ( (LA8_2==RULE_INT||LA8_2==RULE_STRING) ) {
                        alt8=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA8_1==RULE_NAME||(LA8_1>=14 && LA8_1<=18)) ) {
                    alt8=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalArchcnl.g:957:2: ( ( rule__ObjectConceptExpression__Group_0__0 ) )
                    {
                    // InternalArchcnl.g:957:2: ( ( rule__ObjectConceptExpression__Group_0__0 ) )
                    // InternalArchcnl.g:958:3: ( rule__ObjectConceptExpression__Group_0__0 )
                    {
                     before(grammarAccess.getObjectConceptExpressionAccess().getGroup_0()); 
                    // InternalArchcnl.g:959:3: ( rule__ObjectConceptExpression__Group_0__0 )
                    // InternalArchcnl.g:959:4: rule__ObjectConceptExpression__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ObjectConceptExpression__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getObjectConceptExpressionAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:963:2: ( ( rule__ObjectConceptExpression__Group_1__0 ) )
                    {
                    // InternalArchcnl.g:963:2: ( ( rule__ObjectConceptExpression__Group_1__0 ) )
                    // InternalArchcnl.g:964:3: ( rule__ObjectConceptExpression__Group_1__0 )
                    {
                     before(grammarAccess.getObjectConceptExpressionAccess().getGroup_1()); 
                    // InternalArchcnl.g:965:3: ( rule__ObjectConceptExpression__Group_1__0 )
                    // InternalArchcnl.g:965:4: rule__ObjectConceptExpression__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ObjectConceptExpression__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getObjectConceptExpressionAccess().getGroup_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Alternatives"


    // $ANTLR start "rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0"
    // InternalArchcnl.g:973:1: rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0 : ( ( 'at-most' ) | ( 'at-least' ) | ( 'exactly' ) );
    public final void rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:977:1: ( ( 'at-most' ) | ( 'at-least' ) | ( 'exactly' ) )
            int alt9=3;
            switch ( input.LA(1) ) {
            case 14:
                {
                alt9=1;
                }
                break;
            case 15:
                {
                alt9=2;
                }
                break;
            case 16:
                {
                alt9=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // InternalArchcnl.g:978:2: ( 'at-most' )
                    {
                    // InternalArchcnl.g:978:2: ( 'at-most' )
                    // InternalArchcnl.g:979:3: 'at-most'
                    {
                     before(grammarAccess.getObjectConceptExpressionAccess().getCardinalityAtMostKeyword_0_1_0_0_0()); 
                    match(input,14,FOLLOW_2); 
                     after(grammarAccess.getObjectConceptExpressionAccess().getCardinalityAtMostKeyword_0_1_0_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:984:2: ( 'at-least' )
                    {
                    // InternalArchcnl.g:984:2: ( 'at-least' )
                    // InternalArchcnl.g:985:3: 'at-least'
                    {
                     before(grammarAccess.getObjectConceptExpressionAccess().getCardinalityAtLeastKeyword_0_1_0_0_1()); 
                    match(input,15,FOLLOW_2); 
                     after(grammarAccess.getObjectConceptExpressionAccess().getCardinalityAtLeastKeyword_0_1_0_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalArchcnl.g:990:2: ( 'exactly' )
                    {
                    // InternalArchcnl.g:990:2: ( 'exactly' )
                    // InternalArchcnl.g:991:3: 'exactly'
                    {
                     before(grammarAccess.getObjectConceptExpressionAccess().getCardinalityExactlyKeyword_0_1_0_0_2()); 
                    match(input,16,FOLLOW_2); 
                     after(grammarAccess.getObjectConceptExpressionAccess().getCardinalityExactlyKeyword_0_1_0_0_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0"


    // $ANTLR start "rule__Relation__Alternatives"
    // InternalArchcnl.g:1000:1: rule__Relation__Alternatives : ( ( ruleObjectRelation ) | ( ruleDatatypeRelation ) );
    public final void rule__Relation__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1004:1: ( ( ruleObjectRelation ) | ( ruleDatatypeRelation ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_RELATION_NAME) ) {
                int LA10_1 = input.LA(2);

                if ( (LA10_1==33) ) {
                    alt10=2;
                }
                else if ( (LA10_1==EOF||LA10_1==RULE_INT||(LA10_1>=RULE_STRING && LA10_1<=RULE_NAME)||(LA10_1>=14 && LA10_1<=18)||LA10_1==23||LA10_1==29) ) {
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
                    // InternalArchcnl.g:1005:2: ( ruleObjectRelation )
                    {
                    // InternalArchcnl.g:1005:2: ( ruleObjectRelation )
                    // InternalArchcnl.g:1006:3: ruleObjectRelation
                    {
                     before(grammarAccess.getRelationAccess().getObjectRelationParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleObjectRelation();

                    state._fsp--;

                     after(grammarAccess.getRelationAccess().getObjectRelationParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1011:2: ( ruleDatatypeRelation )
                    {
                    // InternalArchcnl.g:1011:2: ( ruleDatatypeRelation )
                    // InternalArchcnl.g:1012:3: ruleDatatypeRelation
                    {
                     before(grammarAccess.getRelationAccess().getDatatypeRelationParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleDatatypeRelation();

                    state._fsp--;

                     after(grammarAccess.getRelationAccess().getDatatypeRelationParserRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Relation__Alternatives"


    // $ANTLR start "rule__ConceptExpression__Alternatives_0"
    // InternalArchcnl.g:1021:1: rule__ConceptExpression__Alternatives_0 : ( ( 'a' ) | ( 'an' ) );
    public final void rule__ConceptExpression__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1025:1: ( ( 'a' ) | ( 'an' ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==17) ) {
                alt11=1;
            }
            else if ( (LA11_0==18) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalArchcnl.g:1026:2: ( 'a' )
                    {
                    // InternalArchcnl.g:1026:2: ( 'a' )
                    // InternalArchcnl.g:1027:3: 'a'
                    {
                     before(grammarAccess.getConceptExpressionAccess().getAKeyword_0_0()); 
                    match(input,17,FOLLOW_2); 
                     after(grammarAccess.getConceptExpressionAccess().getAKeyword_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1032:2: ( 'an' )
                    {
                    // InternalArchcnl.g:1032:2: ( 'an' )
                    // InternalArchcnl.g:1033:3: 'an'
                    {
                     before(grammarAccess.getConceptExpressionAccess().getAnKeyword_0_1()); 
                    match(input,18,FOLLOW_2); 
                     after(grammarAccess.getConceptExpressionAccess().getAnKeyword_0_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptExpression__Alternatives_0"


    // $ANTLR start "rule__StatementList__Alternatives_1_1"
    // InternalArchcnl.g:1042:1: rule__StatementList__Alternatives_1_1 : ( ( ( rule__StatementList__ExpressionAssignment_1_1_0 ) ) | ( ( rule__StatementList__ExpressionAssignment_1_1_1 ) ) | ( ( rule__StatementList__ExpressionAssignment_1_1_2 ) ) );
    public final void rule__StatementList__Alternatives_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1046:1: ( ( ( rule__StatementList__ExpressionAssignment_1_1_0 ) ) | ( ( rule__StatementList__ExpressionAssignment_1_1_1 ) ) | ( ( rule__StatementList__ExpressionAssignment_1_1_2 ) ) )
            int alt12=3;
            switch ( input.LA(1) ) {
            case 17:
            case 18:
                {
                alt12=1;
                }
                break;
            case RULE_NAME:
                {
                int LA12_2 = input.LA(2);

                if ( (LA12_2==EOF||LA12_2==RULE_RELATION_NAME||LA12_2==31||LA12_2==34||LA12_2==36) ) {
                    alt12=1;
                }
                else if ( (LA12_2==RULE_VARIABLE_NAME) ) {
                    alt12=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_INT:
            case RULE_STRING:
                {
                alt12=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // InternalArchcnl.g:1047:2: ( ( rule__StatementList__ExpressionAssignment_1_1_0 ) )
                    {
                    // InternalArchcnl.g:1047:2: ( ( rule__StatementList__ExpressionAssignment_1_1_0 ) )
                    // InternalArchcnl.g:1048:3: ( rule__StatementList__ExpressionAssignment_1_1_0 )
                    {
                     before(grammarAccess.getStatementListAccess().getExpressionAssignment_1_1_0()); 
                    // InternalArchcnl.g:1049:3: ( rule__StatementList__ExpressionAssignment_1_1_0 )
                    // InternalArchcnl.g:1049:4: rule__StatementList__ExpressionAssignment_1_1_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__StatementList__ExpressionAssignment_1_1_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getStatementListAccess().getExpressionAssignment_1_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1053:2: ( ( rule__StatementList__ExpressionAssignment_1_1_1 ) )
                    {
                    // InternalArchcnl.g:1053:2: ( ( rule__StatementList__ExpressionAssignment_1_1_1 ) )
                    // InternalArchcnl.g:1054:3: ( rule__StatementList__ExpressionAssignment_1_1_1 )
                    {
                     before(grammarAccess.getStatementListAccess().getExpressionAssignment_1_1_1()); 
                    // InternalArchcnl.g:1055:3: ( rule__StatementList__ExpressionAssignment_1_1_1 )
                    // InternalArchcnl.g:1055:4: rule__StatementList__ExpressionAssignment_1_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__StatementList__ExpressionAssignment_1_1_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getStatementListAccess().getExpressionAssignment_1_1_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalArchcnl.g:1059:2: ( ( rule__StatementList__ExpressionAssignment_1_1_2 ) )
                    {
                    // InternalArchcnl.g:1059:2: ( ( rule__StatementList__ExpressionAssignment_1_1_2 ) )
                    // InternalArchcnl.g:1060:3: ( rule__StatementList__ExpressionAssignment_1_1_2 )
                    {
                     before(grammarAccess.getStatementListAccess().getExpressionAssignment_1_1_2()); 
                    // InternalArchcnl.g:1061:3: ( rule__StatementList__ExpressionAssignment_1_1_2 )
                    // InternalArchcnl.g:1061:4: rule__StatementList__ExpressionAssignment_1_1_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__StatementList__ExpressionAssignment_1_1_2();

                    state._fsp--;


                    }

                     after(grammarAccess.getStatementListAccess().getExpressionAssignment_1_1_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__Alternatives_1_1"


    // $ANTLR start "rule__DataStatement__Alternatives"
    // InternalArchcnl.g:1069:1: rule__DataStatement__Alternatives : ( ( ( rule__DataStatement__StringValueAssignment_0 ) ) | ( ( rule__DataStatement__IntValueAssignment_1 ) ) );
    public final void rule__DataStatement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1073:1: ( ( ( rule__DataStatement__StringValueAssignment_0 ) ) | ( ( rule__DataStatement__IntValueAssignment_1 ) ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==RULE_STRING) ) {
                alt13=1;
            }
            else if ( (LA13_0==RULE_INT) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // InternalArchcnl.g:1074:2: ( ( rule__DataStatement__StringValueAssignment_0 ) )
                    {
                    // InternalArchcnl.g:1074:2: ( ( rule__DataStatement__StringValueAssignment_0 ) )
                    // InternalArchcnl.g:1075:3: ( rule__DataStatement__StringValueAssignment_0 )
                    {
                     before(grammarAccess.getDataStatementAccess().getStringValueAssignment_0()); 
                    // InternalArchcnl.g:1076:3: ( rule__DataStatement__StringValueAssignment_0 )
                    // InternalArchcnl.g:1076:4: rule__DataStatement__StringValueAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DataStatement__StringValueAssignment_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getDataStatementAccess().getStringValueAssignment_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1080:2: ( ( rule__DataStatement__IntValueAssignment_1 ) )
                    {
                    // InternalArchcnl.g:1080:2: ( ( rule__DataStatement__IntValueAssignment_1 ) )
                    // InternalArchcnl.g:1081:3: ( rule__DataStatement__IntValueAssignment_1 )
                    {
                     before(grammarAccess.getDataStatementAccess().getIntValueAssignment_1()); 
                    // InternalArchcnl.g:1082:3: ( rule__DataStatement__IntValueAssignment_1 )
                    // InternalArchcnl.g:1082:4: rule__DataStatement__IntValueAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__DataStatement__IntValueAssignment_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getDataStatementAccess().getIntValueAssignment_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataStatement__Alternatives"


    // $ANTLR start "rule__FactStatement__Alternatives_0"
    // InternalArchcnl.g:1090:1: rule__FactStatement__Alternatives_0 : ( ( ( rule__FactStatement__AssertionAssignment_0_0 ) ) | ( ( rule__FactStatement__AssertionAssignment_0_1 ) ) );
    public final void rule__FactStatement__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1094:1: ( ( ( rule__FactStatement__AssertionAssignment_0_0 ) ) | ( ( rule__FactStatement__AssertionAssignment_0_1 ) ) )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==RULE_NAME) ) {
                int LA14_1 = input.LA(2);

                if ( (LA14_1==RULE_RELATION_NAME) ) {
                    alt14=2;
                }
                else if ( (LA14_1==37) ) {
                    alt14=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // InternalArchcnl.g:1095:2: ( ( rule__FactStatement__AssertionAssignment_0_0 ) )
                    {
                    // InternalArchcnl.g:1095:2: ( ( rule__FactStatement__AssertionAssignment_0_0 ) )
                    // InternalArchcnl.g:1096:3: ( rule__FactStatement__AssertionAssignment_0_0 )
                    {
                     before(grammarAccess.getFactStatementAccess().getAssertionAssignment_0_0()); 
                    // InternalArchcnl.g:1097:3: ( rule__FactStatement__AssertionAssignment_0_0 )
                    // InternalArchcnl.g:1097:4: rule__FactStatement__AssertionAssignment_0_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__FactStatement__AssertionAssignment_0_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getFactStatementAccess().getAssertionAssignment_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1101:2: ( ( rule__FactStatement__AssertionAssignment_0_1 ) )
                    {
                    // InternalArchcnl.g:1101:2: ( ( rule__FactStatement__AssertionAssignment_0_1 ) )
                    // InternalArchcnl.g:1102:3: ( rule__FactStatement__AssertionAssignment_0_1 )
                    {
                     before(grammarAccess.getFactStatementAccess().getAssertionAssignment_0_1()); 
                    // InternalArchcnl.g:1103:3: ( rule__FactStatement__AssertionAssignment_0_1 )
                    // InternalArchcnl.g:1103:4: rule__FactStatement__AssertionAssignment_0_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__FactStatement__AssertionAssignment_0_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getFactStatementAccess().getAssertionAssignment_0_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FactStatement__Alternatives_0"


    // $ANTLR start "rule__ConceptAssertion__Alternatives_2"
    // InternalArchcnl.g:1111:1: rule__ConceptAssertion__Alternatives_2 : ( ( 'a' ) | ( 'an' ) );
    public final void rule__ConceptAssertion__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1115:1: ( ( 'a' ) | ( 'an' ) )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==17) ) {
                alt15=1;
            }
            else if ( (LA15_0==18) ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // InternalArchcnl.g:1116:2: ( 'a' )
                    {
                    // InternalArchcnl.g:1116:2: ( 'a' )
                    // InternalArchcnl.g:1117:3: 'a'
                    {
                     before(grammarAccess.getConceptAssertionAccess().getAKeyword_2_0()); 
                    match(input,17,FOLLOW_2); 
                     after(grammarAccess.getConceptAssertionAccess().getAKeyword_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1122:2: ( 'an' )
                    {
                    // InternalArchcnl.g:1122:2: ( 'an' )
                    // InternalArchcnl.g:1123:3: 'an'
                    {
                     before(grammarAccess.getConceptAssertionAccess().getAnKeyword_2_1()); 
                    match(input,18,FOLLOW_2); 
                     after(grammarAccess.getConceptAssertionAccess().getAnKeyword_2_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__Alternatives_2"


    // $ANTLR start "rule__RoleAssertion__Alternatives"
    // InternalArchcnl.g:1132:1: rule__RoleAssertion__Alternatives : ( ( ruleObjectPropertyAssertion ) | ( ( rule__RoleAssertion__Group_1__0 ) ) );
    public final void rule__RoleAssertion__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1136:1: ( ( ruleObjectPropertyAssertion ) | ( ( rule__RoleAssertion__Group_1__0 ) ) )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_NAME) ) {
                int LA16_1 = input.LA(2);

                if ( (LA16_1==RULE_RELATION_NAME) ) {
                    int LA16_2 = input.LA(3);

                    if ( (LA16_2==33) ) {
                        int LA16_3 = input.LA(4);

                        if ( (LA16_3==RULE_INT||LA16_3==RULE_STRING) ) {
                            alt16=2;
                        }
                        else if ( ((LA16_3>=17 && LA16_3<=18)) ) {
                            alt16=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 16, 3, input);

                            throw nvae;
                        }
                    }
                    else if ( ((LA16_2>=17 && LA16_2<=18)) ) {
                        alt16=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 16, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // InternalArchcnl.g:1137:2: ( ruleObjectPropertyAssertion )
                    {
                    // InternalArchcnl.g:1137:2: ( ruleObjectPropertyAssertion )
                    // InternalArchcnl.g:1138:3: ruleObjectPropertyAssertion
                    {
                     before(grammarAccess.getRoleAssertionAccess().getObjectPropertyAssertionParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleObjectPropertyAssertion();

                    state._fsp--;

                     after(grammarAccess.getRoleAssertionAccess().getObjectPropertyAssertionParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1143:2: ( ( rule__RoleAssertion__Group_1__0 ) )
                    {
                    // InternalArchcnl.g:1143:2: ( ( rule__RoleAssertion__Group_1__0 ) )
                    // InternalArchcnl.g:1144:3: ( rule__RoleAssertion__Group_1__0 )
                    {
                     before(grammarAccess.getRoleAssertionAccess().getGroup_1()); 
                    // InternalArchcnl.g:1145:3: ( rule__RoleAssertion__Group_1__0 )
                    // InternalArchcnl.g:1145:4: rule__RoleAssertion__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RoleAssertion__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getRoleAssertionAccess().getGroup_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RoleAssertion__Alternatives"


    // $ANTLR start "rule__DatatypePropertyAssertion__Alternatives_2"
    // InternalArchcnl.g:1153:1: rule__DatatypePropertyAssertion__Alternatives_2 : ( ( ( rule__DatatypePropertyAssertion__StringAssignment_2_0 ) ) | ( ( rule__DatatypePropertyAssertion__IntAssignment_2_1 ) ) );
    public final void rule__DatatypePropertyAssertion__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1157:1: ( ( ( rule__DatatypePropertyAssertion__StringAssignment_2_0 ) ) | ( ( rule__DatatypePropertyAssertion__IntAssignment_2_1 ) ) )
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
                    // InternalArchcnl.g:1158:2: ( ( rule__DatatypePropertyAssertion__StringAssignment_2_0 ) )
                    {
                    // InternalArchcnl.g:1158:2: ( ( rule__DatatypePropertyAssertion__StringAssignment_2_0 ) )
                    // InternalArchcnl.g:1159:3: ( rule__DatatypePropertyAssertion__StringAssignment_2_0 )
                    {
                     before(grammarAccess.getDatatypePropertyAssertionAccess().getStringAssignment_2_0()); 
                    // InternalArchcnl.g:1160:3: ( rule__DatatypePropertyAssertion__StringAssignment_2_0 )
                    // InternalArchcnl.g:1160:4: rule__DatatypePropertyAssertion__StringAssignment_2_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DatatypePropertyAssertion__StringAssignment_2_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getDatatypePropertyAssertionAccess().getStringAssignment_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1164:2: ( ( rule__DatatypePropertyAssertion__IntAssignment_2_1 ) )
                    {
                    // InternalArchcnl.g:1164:2: ( ( rule__DatatypePropertyAssertion__IntAssignment_2_1 ) )
                    // InternalArchcnl.g:1165:3: ( rule__DatatypePropertyAssertion__IntAssignment_2_1 )
                    {
                     before(grammarAccess.getDatatypePropertyAssertionAccess().getIntAssignment_2_1()); 
                    // InternalArchcnl.g:1166:3: ( rule__DatatypePropertyAssertion__IntAssignment_2_1 )
                    // InternalArchcnl.g:1166:4: rule__DatatypePropertyAssertion__IntAssignment_2_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__DatatypePropertyAssertion__IntAssignment_2_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getDatatypePropertyAssertionAccess().getIntAssignment_2_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypePropertyAssertion__Alternatives_2"


    // $ANTLR start "rule__ObjectPropertyAssertion__Alternatives_2"
    // InternalArchcnl.g:1174:1: rule__ObjectPropertyAssertion__Alternatives_2 : ( ( 'a' ) | ( 'an' ) );
    public final void rule__ObjectPropertyAssertion__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1178:1: ( ( 'a' ) | ( 'an' ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==17) ) {
                alt18=1;
            }
            else if ( (LA18_0==18) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // InternalArchcnl.g:1179:2: ( 'a' )
                    {
                    // InternalArchcnl.g:1179:2: ( 'a' )
                    // InternalArchcnl.g:1180:3: 'a'
                    {
                     before(grammarAccess.getObjectPropertyAssertionAccess().getAKeyword_2_0()); 
                    match(input,17,FOLLOW_2); 
                     after(grammarAccess.getObjectPropertyAssertionAccess().getAKeyword_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:1185:2: ( 'an' )
                    {
                    // InternalArchcnl.g:1185:2: ( 'an' )
                    // InternalArchcnl.g:1186:3: 'an'
                    {
                     before(grammarAccess.getObjectPropertyAssertionAccess().getAnKeyword_2_1()); 
                    match(input,18,FOLLOW_2); 
                     after(grammarAccess.getObjectPropertyAssertionAccess().getAnKeyword_2_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__Alternatives_2"


    // $ANTLR start "rule__Sentence__Group_0__0"
    // InternalArchcnl.g:1195:1: rule__Sentence__Group_0__0 : rule__Sentence__Group_0__0__Impl rule__Sentence__Group_0__1 ;
    public final void rule__Sentence__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1199:1: ( rule__Sentence__Group_0__0__Impl rule__Sentence__Group_0__1 )
            // InternalArchcnl.g:1200:2: rule__Sentence__Group_0__0__Impl rule__Sentence__Group_0__1
            {
            pushFollow(FOLLOW_4);
            rule__Sentence__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Sentence__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__Group_0__0"


    // $ANTLR start "rule__Sentence__Group_0__0__Impl"
    // InternalArchcnl.g:1207:1: rule__Sentence__Group_0__0__Impl : ( 'Every' ) ;
    public final void rule__Sentence__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1211:1: ( ( 'Every' ) )
            // InternalArchcnl.g:1212:1: ( 'Every' )
            {
            // InternalArchcnl.g:1212:1: ( 'Every' )
            // InternalArchcnl.g:1213:2: 'Every'
            {
             before(grammarAccess.getSentenceAccess().getEveryKeyword_0_0()); 
            match(input,19,FOLLOW_2); 
             after(grammarAccess.getSentenceAccess().getEveryKeyword_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__Group_0__0__Impl"


    // $ANTLR start "rule__Sentence__Group_0__1"
    // InternalArchcnl.g:1222:1: rule__Sentence__Group_0__1 : rule__Sentence__Group_0__1__Impl rule__Sentence__Group_0__2 ;
    public final void rule__Sentence__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1226:1: ( rule__Sentence__Group_0__1__Impl rule__Sentence__Group_0__2 )
            // InternalArchcnl.g:1227:2: rule__Sentence__Group_0__1__Impl rule__Sentence__Group_0__2
            {
            pushFollow(FOLLOW_5);
            rule__Sentence__Group_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Sentence__Group_0__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__Group_0__1"


    // $ANTLR start "rule__Sentence__Group_0__1__Impl"
    // InternalArchcnl.g:1234:1: rule__Sentence__Group_0__1__Impl : ( ( rule__Sentence__SubjectAssignment_0_1 ) ) ;
    public final void rule__Sentence__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1238:1: ( ( ( rule__Sentence__SubjectAssignment_0_1 ) ) )
            // InternalArchcnl.g:1239:1: ( ( rule__Sentence__SubjectAssignment_0_1 ) )
            {
            // InternalArchcnl.g:1239:1: ( ( rule__Sentence__SubjectAssignment_0_1 ) )
            // InternalArchcnl.g:1240:2: ( rule__Sentence__SubjectAssignment_0_1 )
            {
             before(grammarAccess.getSentenceAccess().getSubjectAssignment_0_1()); 
            // InternalArchcnl.g:1241:2: ( rule__Sentence__SubjectAssignment_0_1 )
            // InternalArchcnl.g:1241:3: rule__Sentence__SubjectAssignment_0_1
            {
            pushFollow(FOLLOW_2);
            rule__Sentence__SubjectAssignment_0_1();

            state._fsp--;


            }

             after(grammarAccess.getSentenceAccess().getSubjectAssignment_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__Group_0__1__Impl"


    // $ANTLR start "rule__Sentence__Group_0__2"
    // InternalArchcnl.g:1249:1: rule__Sentence__Group_0__2 : rule__Sentence__Group_0__2__Impl ;
    public final void rule__Sentence__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1253:1: ( rule__Sentence__Group_0__2__Impl )
            // InternalArchcnl.g:1254:2: rule__Sentence__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Sentence__Group_0__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__Group_0__2"


    // $ANTLR start "rule__Sentence__Group_0__2__Impl"
    // InternalArchcnl.g:1260:1: rule__Sentence__Group_0__2__Impl : ( ( rule__Sentence__RuletypeAssignment_0_2 ) ) ;
    public final void rule__Sentence__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1264:1: ( ( ( rule__Sentence__RuletypeAssignment_0_2 ) ) )
            // InternalArchcnl.g:1265:1: ( ( rule__Sentence__RuletypeAssignment_0_2 ) )
            {
            // InternalArchcnl.g:1265:1: ( ( rule__Sentence__RuletypeAssignment_0_2 ) )
            // InternalArchcnl.g:1266:2: ( rule__Sentence__RuletypeAssignment_0_2 )
            {
             before(grammarAccess.getSentenceAccess().getRuletypeAssignment_0_2()); 
            // InternalArchcnl.g:1267:2: ( rule__Sentence__RuletypeAssignment_0_2 )
            // InternalArchcnl.g:1267:3: rule__Sentence__RuletypeAssignment_0_2
            {
            pushFollow(FOLLOW_2);
            rule__Sentence__RuletypeAssignment_0_2();

            state._fsp--;


            }

             after(grammarAccess.getSentenceAccess().getRuletypeAssignment_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__Group_0__2__Impl"


    // $ANTLR start "rule__NegationRuleType__Group_1__0"
    // InternalArchcnl.g:1276:1: rule__NegationRuleType__Group_1__0 : rule__NegationRuleType__Group_1__0__Impl rule__NegationRuleType__Group_1__1 ;
    public final void rule__NegationRuleType__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1280:1: ( rule__NegationRuleType__Group_1__0__Impl rule__NegationRuleType__Group_1__1 )
            // InternalArchcnl.g:1281:2: rule__NegationRuleType__Group_1__0__Impl rule__NegationRuleType__Group_1__1
            {
            pushFollow(FOLLOW_6);
            rule__NegationRuleType__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__NegationRuleType__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1__0"


    // $ANTLR start "rule__NegationRuleType__Group_1__0__Impl"
    // InternalArchcnl.g:1288:1: rule__NegationRuleType__Group_1__0__Impl : ( ( rule__NegationRuleType__Group_1_0__0 ) ) ;
    public final void rule__NegationRuleType__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1292:1: ( ( ( rule__NegationRuleType__Group_1_0__0 ) ) )
            // InternalArchcnl.g:1293:1: ( ( rule__NegationRuleType__Group_1_0__0 ) )
            {
            // InternalArchcnl.g:1293:1: ( ( rule__NegationRuleType__Group_1_0__0 ) )
            // InternalArchcnl.g:1294:2: ( rule__NegationRuleType__Group_1_0__0 )
            {
             before(grammarAccess.getNegationRuleTypeAccess().getGroup_1_0()); 
            // InternalArchcnl.g:1295:2: ( rule__NegationRuleType__Group_1_0__0 )
            // InternalArchcnl.g:1295:3: rule__NegationRuleType__Group_1_0__0
            {
            pushFollow(FOLLOW_2);
            rule__NegationRuleType__Group_1_0__0();

            state._fsp--;


            }

             after(grammarAccess.getNegationRuleTypeAccess().getGroup_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1__0__Impl"


    // $ANTLR start "rule__NegationRuleType__Group_1__1"
    // InternalArchcnl.g:1303:1: rule__NegationRuleType__Group_1__1 : rule__NegationRuleType__Group_1__1__Impl ;
    public final void rule__NegationRuleType__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1307:1: ( rule__NegationRuleType__Group_1__1__Impl )
            // InternalArchcnl.g:1308:2: rule__NegationRuleType__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__NegationRuleType__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1__1"


    // $ANTLR start "rule__NegationRuleType__Group_1__1__Impl"
    // InternalArchcnl.g:1314:1: rule__NegationRuleType__Group_1__1__Impl : ( '.' ) ;
    public final void rule__NegationRuleType__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1318:1: ( ( '.' ) )
            // InternalArchcnl.g:1319:1: ( '.' )
            {
            // InternalArchcnl.g:1319:1: ( '.' )
            // InternalArchcnl.g:1320:2: '.'
            {
             before(grammarAccess.getNegationRuleTypeAccess().getFullStopKeyword_1_1()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getNegationRuleTypeAccess().getFullStopKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1__1__Impl"


    // $ANTLR start "rule__NegationRuleType__Group_1_0__0"
    // InternalArchcnl.g:1330:1: rule__NegationRuleType__Group_1_0__0 : rule__NegationRuleType__Group_1_0__0__Impl rule__NegationRuleType__Group_1_0__1 ;
    public final void rule__NegationRuleType__Group_1_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1334:1: ( rule__NegationRuleType__Group_1_0__0__Impl rule__NegationRuleType__Group_1_0__1 )
            // InternalArchcnl.g:1335:2: rule__NegationRuleType__Group_1_0__0__Impl rule__NegationRuleType__Group_1_0__1
            {
            pushFollow(FOLLOW_4);
            rule__NegationRuleType__Group_1_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__NegationRuleType__Group_1_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1_0__0"


    // $ANTLR start "rule__NegationRuleType__Group_1_0__0__Impl"
    // InternalArchcnl.g:1342:1: rule__NegationRuleType__Group_1_0__0__Impl : ( 'No' ) ;
    public final void rule__NegationRuleType__Group_1_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1346:1: ( ( 'No' ) )
            // InternalArchcnl.g:1347:1: ( 'No' )
            {
            // InternalArchcnl.g:1347:1: ( 'No' )
            // InternalArchcnl.g:1348:2: 'No'
            {
             before(grammarAccess.getNegationRuleTypeAccess().getNoKeyword_1_0_0()); 
            match(input,21,FOLLOW_2); 
             after(grammarAccess.getNegationRuleTypeAccess().getNoKeyword_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1_0__0__Impl"


    // $ANTLR start "rule__NegationRuleType__Group_1_0__1"
    // InternalArchcnl.g:1357:1: rule__NegationRuleType__Group_1_0__1 : rule__NegationRuleType__Group_1_0__1__Impl rule__NegationRuleType__Group_1_0__2 ;
    public final void rule__NegationRuleType__Group_1_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1361:1: ( rule__NegationRuleType__Group_1_0__1__Impl rule__NegationRuleType__Group_1_0__2 )
            // InternalArchcnl.g:1362:2: rule__NegationRuleType__Group_1_0__1__Impl rule__NegationRuleType__Group_1_0__2
            {
            pushFollow(FOLLOW_7);
            rule__NegationRuleType__Group_1_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__NegationRuleType__Group_1_0__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1_0__1"


    // $ANTLR start "rule__NegationRuleType__Group_1_0__1__Impl"
    // InternalArchcnl.g:1369:1: rule__NegationRuleType__Group_1_0__1__Impl : ( ( rule__NegationRuleType__SubjectAssignment_1_0_1 ) ) ;
    public final void rule__NegationRuleType__Group_1_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1373:1: ( ( ( rule__NegationRuleType__SubjectAssignment_1_0_1 ) ) )
            // InternalArchcnl.g:1374:1: ( ( rule__NegationRuleType__SubjectAssignment_1_0_1 ) )
            {
            // InternalArchcnl.g:1374:1: ( ( rule__NegationRuleType__SubjectAssignment_1_0_1 ) )
            // InternalArchcnl.g:1375:2: ( rule__NegationRuleType__SubjectAssignment_1_0_1 )
            {
             before(grammarAccess.getNegationRuleTypeAccess().getSubjectAssignment_1_0_1()); 
            // InternalArchcnl.g:1376:2: ( rule__NegationRuleType__SubjectAssignment_1_0_1 )
            // InternalArchcnl.g:1376:3: rule__NegationRuleType__SubjectAssignment_1_0_1
            {
            pushFollow(FOLLOW_2);
            rule__NegationRuleType__SubjectAssignment_1_0_1();

            state._fsp--;


            }

             after(grammarAccess.getNegationRuleTypeAccess().getSubjectAssignment_1_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1_0__1__Impl"


    // $ANTLR start "rule__NegationRuleType__Group_1_0__2"
    // InternalArchcnl.g:1384:1: rule__NegationRuleType__Group_1_0__2 : rule__NegationRuleType__Group_1_0__2__Impl rule__NegationRuleType__Group_1_0__3 ;
    public final void rule__NegationRuleType__Group_1_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1388:1: ( rule__NegationRuleType__Group_1_0__2__Impl rule__NegationRuleType__Group_1_0__3 )
            // InternalArchcnl.g:1389:2: rule__NegationRuleType__Group_1_0__2__Impl rule__NegationRuleType__Group_1_0__3
            {
            pushFollow(FOLLOW_8);
            rule__NegationRuleType__Group_1_0__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__NegationRuleType__Group_1_0__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1_0__2"


    // $ANTLR start "rule__NegationRuleType__Group_1_0__2__Impl"
    // InternalArchcnl.g:1396:1: rule__NegationRuleType__Group_1_0__2__Impl : ( 'can' ) ;
    public final void rule__NegationRuleType__Group_1_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1400:1: ( ( 'can' ) )
            // InternalArchcnl.g:1401:1: ( 'can' )
            {
            // InternalArchcnl.g:1401:1: ( 'can' )
            // InternalArchcnl.g:1402:2: 'can'
            {
             before(grammarAccess.getNegationRuleTypeAccess().getCanKeyword_1_0_2()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getNegationRuleTypeAccess().getCanKeyword_1_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1_0__2__Impl"


    // $ANTLR start "rule__NegationRuleType__Group_1_0__3"
    // InternalArchcnl.g:1411:1: rule__NegationRuleType__Group_1_0__3 : rule__NegationRuleType__Group_1_0__3__Impl ;
    public final void rule__NegationRuleType__Group_1_0__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1415:1: ( rule__NegationRuleType__Group_1_0__3__Impl )
            // InternalArchcnl.g:1416:2: rule__NegationRuleType__Group_1_0__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__NegationRuleType__Group_1_0__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1_0__3"


    // $ANTLR start "rule__NegationRuleType__Group_1_0__3__Impl"
    // InternalArchcnl.g:1422:1: rule__NegationRuleType__Group_1_0__3__Impl : ( ( rule__NegationRuleType__ObjectAssignment_1_0_3 ) ) ;
    public final void rule__NegationRuleType__Group_1_0__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1426:1: ( ( ( rule__NegationRuleType__ObjectAssignment_1_0_3 ) ) )
            // InternalArchcnl.g:1427:1: ( ( rule__NegationRuleType__ObjectAssignment_1_0_3 ) )
            {
            // InternalArchcnl.g:1427:1: ( ( rule__NegationRuleType__ObjectAssignment_1_0_3 ) )
            // InternalArchcnl.g:1428:2: ( rule__NegationRuleType__ObjectAssignment_1_0_3 )
            {
             before(grammarAccess.getNegationRuleTypeAccess().getObjectAssignment_1_0_3()); 
            // InternalArchcnl.g:1429:2: ( rule__NegationRuleType__ObjectAssignment_1_0_3 )
            // InternalArchcnl.g:1429:3: rule__NegationRuleType__ObjectAssignment_1_0_3
            {
            pushFollow(FOLLOW_2);
            rule__NegationRuleType__ObjectAssignment_1_0_3();

            state._fsp--;


            }

             after(grammarAccess.getNegationRuleTypeAccess().getObjectAssignment_1_0_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__Group_1_0__3__Impl"


    // $ANTLR start "rule__Anything__Group__0"
    // InternalArchcnl.g:1438:1: rule__Anything__Group__0 : rule__Anything__Group__0__Impl rule__Anything__Group__1 ;
    public final void rule__Anything__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1442:1: ( rule__Anything__Group__0__Impl rule__Anything__Group__1 )
            // InternalArchcnl.g:1443:2: rule__Anything__Group__0__Impl rule__Anything__Group__1
            {
            pushFollow(FOLLOW_9);
            rule__Anything__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Anything__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Anything__Group__0"


    // $ANTLR start "rule__Anything__Group__0__Impl"
    // InternalArchcnl.g:1450:1: rule__Anything__Group__0__Impl : ( ( rule__Anything__RelationAssignment_0 ) ) ;
    public final void rule__Anything__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1454:1: ( ( ( rule__Anything__RelationAssignment_0 ) ) )
            // InternalArchcnl.g:1455:1: ( ( rule__Anything__RelationAssignment_0 ) )
            {
            // InternalArchcnl.g:1455:1: ( ( rule__Anything__RelationAssignment_0 ) )
            // InternalArchcnl.g:1456:2: ( rule__Anything__RelationAssignment_0 )
            {
             before(grammarAccess.getAnythingAccess().getRelationAssignment_0()); 
            // InternalArchcnl.g:1457:2: ( rule__Anything__RelationAssignment_0 )
            // InternalArchcnl.g:1457:3: rule__Anything__RelationAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__Anything__RelationAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getAnythingAccess().getRelationAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Anything__Group__0__Impl"


    // $ANTLR start "rule__Anything__Group__1"
    // InternalArchcnl.g:1465:1: rule__Anything__Group__1 : rule__Anything__Group__1__Impl ;
    public final void rule__Anything__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1469:1: ( rule__Anything__Group__1__Impl )
            // InternalArchcnl.g:1470:2: rule__Anything__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Anything__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Anything__Group__1"


    // $ANTLR start "rule__Anything__Group__1__Impl"
    // InternalArchcnl.g:1476:1: rule__Anything__Group__1__Impl : ( 'anything' ) ;
    public final void rule__Anything__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1480:1: ( ( 'anything' ) )
            // InternalArchcnl.g:1481:1: ( 'anything' )
            {
            // InternalArchcnl.g:1481:1: ( 'anything' )
            // InternalArchcnl.g:1482:2: 'anything'
            {
             before(grammarAccess.getAnythingAccess().getAnythingKeyword_1()); 
            match(input,23,FOLLOW_2); 
             after(grammarAccess.getAnythingAccess().getAnythingKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Anything__Group__1__Impl"


    // $ANTLR start "rule__Nothing__Group__0"
    // InternalArchcnl.g:1492:1: rule__Nothing__Group__0 : rule__Nothing__Group__0__Impl rule__Nothing__Group__1 ;
    public final void rule__Nothing__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1496:1: ( rule__Nothing__Group__0__Impl rule__Nothing__Group__1 )
            // InternalArchcnl.g:1497:2: rule__Nothing__Group__0__Impl rule__Nothing__Group__1
            {
            pushFollow(FOLLOW_7);
            rule__Nothing__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Nothing__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Nothing__Group__0"


    // $ANTLR start "rule__Nothing__Group__0__Impl"
    // InternalArchcnl.g:1504:1: rule__Nothing__Group__0__Impl : ( 'Nothing' ) ;
    public final void rule__Nothing__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1508:1: ( ( 'Nothing' ) )
            // InternalArchcnl.g:1509:1: ( 'Nothing' )
            {
            // InternalArchcnl.g:1509:1: ( 'Nothing' )
            // InternalArchcnl.g:1510:2: 'Nothing'
            {
             before(grammarAccess.getNothingAccess().getNothingKeyword_0()); 
            match(input,24,FOLLOW_2); 
             after(grammarAccess.getNothingAccess().getNothingKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Nothing__Group__0__Impl"


    // $ANTLR start "rule__Nothing__Group__1"
    // InternalArchcnl.g:1519:1: rule__Nothing__Group__1 : rule__Nothing__Group__1__Impl rule__Nothing__Group__2 ;
    public final void rule__Nothing__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1523:1: ( rule__Nothing__Group__1__Impl rule__Nothing__Group__2 )
            // InternalArchcnl.g:1524:2: rule__Nothing__Group__1__Impl rule__Nothing__Group__2
            {
            pushFollow(FOLLOW_8);
            rule__Nothing__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Nothing__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Nothing__Group__1"


    // $ANTLR start "rule__Nothing__Group__1__Impl"
    // InternalArchcnl.g:1531:1: rule__Nothing__Group__1__Impl : ( 'can' ) ;
    public final void rule__Nothing__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1535:1: ( ( 'can' ) )
            // InternalArchcnl.g:1536:1: ( 'can' )
            {
            // InternalArchcnl.g:1536:1: ( 'can' )
            // InternalArchcnl.g:1537:2: 'can'
            {
             before(grammarAccess.getNothingAccess().getCanKeyword_1()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getNothingAccess().getCanKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Nothing__Group__1__Impl"


    // $ANTLR start "rule__Nothing__Group__2"
    // InternalArchcnl.g:1546:1: rule__Nothing__Group__2 : rule__Nothing__Group__2__Impl rule__Nothing__Group__3 ;
    public final void rule__Nothing__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1550:1: ( rule__Nothing__Group__2__Impl rule__Nothing__Group__3 )
            // InternalArchcnl.g:1551:2: rule__Nothing__Group__2__Impl rule__Nothing__Group__3
            {
            pushFollow(FOLLOW_6);
            rule__Nothing__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Nothing__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Nothing__Group__2"


    // $ANTLR start "rule__Nothing__Group__2__Impl"
    // InternalArchcnl.g:1558:1: rule__Nothing__Group__2__Impl : ( ( rule__Nothing__ObjectAssignment_2 ) ) ;
    public final void rule__Nothing__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1562:1: ( ( ( rule__Nothing__ObjectAssignment_2 ) ) )
            // InternalArchcnl.g:1563:1: ( ( rule__Nothing__ObjectAssignment_2 ) )
            {
            // InternalArchcnl.g:1563:1: ( ( rule__Nothing__ObjectAssignment_2 ) )
            // InternalArchcnl.g:1564:2: ( rule__Nothing__ObjectAssignment_2 )
            {
             before(grammarAccess.getNothingAccess().getObjectAssignment_2()); 
            // InternalArchcnl.g:1565:2: ( rule__Nothing__ObjectAssignment_2 )
            // InternalArchcnl.g:1565:3: rule__Nothing__ObjectAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Nothing__ObjectAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getNothingAccess().getObjectAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Nothing__Group__2__Impl"


    // $ANTLR start "rule__Nothing__Group__3"
    // InternalArchcnl.g:1573:1: rule__Nothing__Group__3 : rule__Nothing__Group__3__Impl ;
    public final void rule__Nothing__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1577:1: ( rule__Nothing__Group__3__Impl )
            // InternalArchcnl.g:1578:2: rule__Nothing__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Nothing__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Nothing__Group__3"


    // $ANTLR start "rule__Nothing__Group__3__Impl"
    // InternalArchcnl.g:1584:1: rule__Nothing__Group__3__Impl : ( '.' ) ;
    public final void rule__Nothing__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1588:1: ( ( '.' ) )
            // InternalArchcnl.g:1589:1: ( '.' )
            {
            // InternalArchcnl.g:1589:1: ( '.' )
            // InternalArchcnl.g:1590:2: '.'
            {
             before(grammarAccess.getNothingAccess().getFullStopKeyword_3()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getNothingAccess().getFullStopKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Nothing__Group__3__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__0"
    // InternalArchcnl.g:1600:1: rule__ConditionalRuleType__Group__0 : rule__ConditionalRuleType__Group__0__Impl rule__ConditionalRuleType__Group__1 ;
    public final void rule__ConditionalRuleType__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1604:1: ( rule__ConditionalRuleType__Group__0__Impl rule__ConditionalRuleType__Group__1 )
            // InternalArchcnl.g:1605:2: rule__ConditionalRuleType__Group__0__Impl rule__ConditionalRuleType__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__ConditionalRuleType__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__0"


    // $ANTLR start "rule__ConditionalRuleType__Group__0__Impl"
    // InternalArchcnl.g:1612:1: rule__ConditionalRuleType__Group__0__Impl : ( ( rule__ConditionalRuleType__StartAssignment_0 ) ) ;
    public final void rule__ConditionalRuleType__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1616:1: ( ( ( rule__ConditionalRuleType__StartAssignment_0 ) ) )
            // InternalArchcnl.g:1617:1: ( ( rule__ConditionalRuleType__StartAssignment_0 ) )
            {
            // InternalArchcnl.g:1617:1: ( ( rule__ConditionalRuleType__StartAssignment_0 ) )
            // InternalArchcnl.g:1618:2: ( rule__ConditionalRuleType__StartAssignment_0 )
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getStartAssignment_0()); 
            // InternalArchcnl.g:1619:2: ( rule__ConditionalRuleType__StartAssignment_0 )
            // InternalArchcnl.g:1619:3: rule__ConditionalRuleType__StartAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__StartAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getConditionalRuleTypeAccess().getStartAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__0__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__1"
    // InternalArchcnl.g:1627:1: rule__ConditionalRuleType__Group__1 : rule__ConditionalRuleType__Group__1__Impl rule__ConditionalRuleType__Group__2 ;
    public final void rule__ConditionalRuleType__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1631:1: ( rule__ConditionalRuleType__Group__1__Impl rule__ConditionalRuleType__Group__2 )
            // InternalArchcnl.g:1632:2: rule__ConditionalRuleType__Group__1__Impl rule__ConditionalRuleType__Group__2
            {
            pushFollow(FOLLOW_8);
            rule__ConditionalRuleType__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__1"


    // $ANTLR start "rule__ConditionalRuleType__Group__1__Impl"
    // InternalArchcnl.g:1639:1: rule__ConditionalRuleType__Group__1__Impl : ( ( rule__ConditionalRuleType__SubjectAssignment_1 ) ) ;
    public final void rule__ConditionalRuleType__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1643:1: ( ( ( rule__ConditionalRuleType__SubjectAssignment_1 ) ) )
            // InternalArchcnl.g:1644:1: ( ( rule__ConditionalRuleType__SubjectAssignment_1 ) )
            {
            // InternalArchcnl.g:1644:1: ( ( rule__ConditionalRuleType__SubjectAssignment_1 ) )
            // InternalArchcnl.g:1645:2: ( rule__ConditionalRuleType__SubjectAssignment_1 )
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getSubjectAssignment_1()); 
            // InternalArchcnl.g:1646:2: ( rule__ConditionalRuleType__SubjectAssignment_1 )
            // InternalArchcnl.g:1646:3: rule__ConditionalRuleType__SubjectAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__SubjectAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getConditionalRuleTypeAccess().getSubjectAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__1__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__2"
    // InternalArchcnl.g:1654:1: rule__ConditionalRuleType__Group__2 : rule__ConditionalRuleType__Group__2__Impl rule__ConditionalRuleType__Group__3 ;
    public final void rule__ConditionalRuleType__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1658:1: ( rule__ConditionalRuleType__Group__2__Impl rule__ConditionalRuleType__Group__3 )
            // InternalArchcnl.g:1659:2: rule__ConditionalRuleType__Group__2__Impl rule__ConditionalRuleType__Group__3
            {
            pushFollow(FOLLOW_4);
            rule__ConditionalRuleType__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__2"


    // $ANTLR start "rule__ConditionalRuleType__Group__2__Impl"
    // InternalArchcnl.g:1666:1: rule__ConditionalRuleType__Group__2__Impl : ( ( rule__ConditionalRuleType__RelationAssignment_2 ) ) ;
    public final void rule__ConditionalRuleType__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1670:1: ( ( ( rule__ConditionalRuleType__RelationAssignment_2 ) ) )
            // InternalArchcnl.g:1671:1: ( ( rule__ConditionalRuleType__RelationAssignment_2 ) )
            {
            // InternalArchcnl.g:1671:1: ( ( rule__ConditionalRuleType__RelationAssignment_2 ) )
            // InternalArchcnl.g:1672:2: ( rule__ConditionalRuleType__RelationAssignment_2 )
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getRelationAssignment_2()); 
            // InternalArchcnl.g:1673:2: ( rule__ConditionalRuleType__RelationAssignment_2 )
            // InternalArchcnl.g:1673:3: rule__ConditionalRuleType__RelationAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__RelationAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getConditionalRuleTypeAccess().getRelationAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__2__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__3"
    // InternalArchcnl.g:1681:1: rule__ConditionalRuleType__Group__3 : rule__ConditionalRuleType__Group__3__Impl rule__ConditionalRuleType__Group__4 ;
    public final void rule__ConditionalRuleType__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1685:1: ( rule__ConditionalRuleType__Group__3__Impl rule__ConditionalRuleType__Group__4 )
            // InternalArchcnl.g:1686:2: rule__ConditionalRuleType__Group__3__Impl rule__ConditionalRuleType__Group__4
            {
            pushFollow(FOLLOW_10);
            rule__ConditionalRuleType__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__3"


    // $ANTLR start "rule__ConditionalRuleType__Group__3__Impl"
    // InternalArchcnl.g:1693:1: rule__ConditionalRuleType__Group__3__Impl : ( ( rule__ConditionalRuleType__ObjectAssignment_3 ) ) ;
    public final void rule__ConditionalRuleType__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1697:1: ( ( ( rule__ConditionalRuleType__ObjectAssignment_3 ) ) )
            // InternalArchcnl.g:1698:1: ( ( rule__ConditionalRuleType__ObjectAssignment_3 ) )
            {
            // InternalArchcnl.g:1698:1: ( ( rule__ConditionalRuleType__ObjectAssignment_3 ) )
            // InternalArchcnl.g:1699:2: ( rule__ConditionalRuleType__ObjectAssignment_3 )
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getObjectAssignment_3()); 
            // InternalArchcnl.g:1700:2: ( rule__ConditionalRuleType__ObjectAssignment_3 )
            // InternalArchcnl.g:1700:3: rule__ConditionalRuleType__ObjectAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__ObjectAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getConditionalRuleTypeAccess().getObjectAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__3__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__4"
    // InternalArchcnl.g:1708:1: rule__ConditionalRuleType__Group__4 : rule__ConditionalRuleType__Group__4__Impl rule__ConditionalRuleType__Group__5 ;
    public final void rule__ConditionalRuleType__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1712:1: ( rule__ConditionalRuleType__Group__4__Impl rule__ConditionalRuleType__Group__5 )
            // InternalArchcnl.g:1713:2: rule__ConditionalRuleType__Group__4__Impl rule__ConditionalRuleType__Group__5
            {
            pushFollow(FOLLOW_11);
            rule__ConditionalRuleType__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__4"


    // $ANTLR start "rule__ConditionalRuleType__Group__4__Impl"
    // InternalArchcnl.g:1720:1: rule__ConditionalRuleType__Group__4__Impl : ( ',' ) ;
    public final void rule__ConditionalRuleType__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1724:1: ( ( ',' ) )
            // InternalArchcnl.g:1725:1: ( ',' )
            {
            // InternalArchcnl.g:1725:1: ( ',' )
            // InternalArchcnl.g:1726:2: ','
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getCommaKeyword_4()); 
            match(input,25,FOLLOW_2); 
             after(grammarAccess.getConditionalRuleTypeAccess().getCommaKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__4__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__5"
    // InternalArchcnl.g:1735:1: rule__ConditionalRuleType__Group__5 : rule__ConditionalRuleType__Group__5__Impl rule__ConditionalRuleType__Group__6 ;
    public final void rule__ConditionalRuleType__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1739:1: ( rule__ConditionalRuleType__Group__5__Impl rule__ConditionalRuleType__Group__6 )
            // InternalArchcnl.g:1740:2: rule__ConditionalRuleType__Group__5__Impl rule__ConditionalRuleType__Group__6
            {
            pushFollow(FOLLOW_12);
            rule__ConditionalRuleType__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__5"


    // $ANTLR start "rule__ConditionalRuleType__Group__5__Impl"
    // InternalArchcnl.g:1747:1: rule__ConditionalRuleType__Group__5__Impl : ( 'then' ) ;
    public final void rule__ConditionalRuleType__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1751:1: ( ( 'then' ) )
            // InternalArchcnl.g:1752:1: ( 'then' )
            {
            // InternalArchcnl.g:1752:1: ( 'then' )
            // InternalArchcnl.g:1753:2: 'then'
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getThenKeyword_5()); 
            match(input,26,FOLLOW_2); 
             after(grammarAccess.getConditionalRuleTypeAccess().getThenKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__5__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__6"
    // InternalArchcnl.g:1762:1: rule__ConditionalRuleType__Group__6 : rule__ConditionalRuleType__Group__6__Impl rule__ConditionalRuleType__Group__7 ;
    public final void rule__ConditionalRuleType__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1766:1: ( rule__ConditionalRuleType__Group__6__Impl rule__ConditionalRuleType__Group__7 )
            // InternalArchcnl.g:1767:2: rule__ConditionalRuleType__Group__6__Impl rule__ConditionalRuleType__Group__7
            {
            pushFollow(FOLLOW_13);
            rule__ConditionalRuleType__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__6"


    // $ANTLR start "rule__ConditionalRuleType__Group__6__Impl"
    // InternalArchcnl.g:1774:1: rule__ConditionalRuleType__Group__6__Impl : ( 'it' ) ;
    public final void rule__ConditionalRuleType__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1778:1: ( ( 'it' ) )
            // InternalArchcnl.g:1779:1: ( 'it' )
            {
            // InternalArchcnl.g:1779:1: ( 'it' )
            // InternalArchcnl.g:1780:2: 'it'
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getItKeyword_6()); 
            match(input,27,FOLLOW_2); 
             after(grammarAccess.getConditionalRuleTypeAccess().getItKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__6__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__7"
    // InternalArchcnl.g:1789:1: rule__ConditionalRuleType__Group__7 : rule__ConditionalRuleType__Group__7__Impl rule__ConditionalRuleType__Group__8 ;
    public final void rule__ConditionalRuleType__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1793:1: ( rule__ConditionalRuleType__Group__7__Impl rule__ConditionalRuleType__Group__8 )
            // InternalArchcnl.g:1794:2: rule__ConditionalRuleType__Group__7__Impl rule__ConditionalRuleType__Group__8
            {
            pushFollow(FOLLOW_8);
            rule__ConditionalRuleType__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__7"


    // $ANTLR start "rule__ConditionalRuleType__Group__7__Impl"
    // InternalArchcnl.g:1801:1: rule__ConditionalRuleType__Group__7__Impl : ( 'must' ) ;
    public final void rule__ConditionalRuleType__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1805:1: ( ( 'must' ) )
            // InternalArchcnl.g:1806:1: ( 'must' )
            {
            // InternalArchcnl.g:1806:1: ( 'must' )
            // InternalArchcnl.g:1807:2: 'must'
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getMustKeyword_7()); 
            match(input,28,FOLLOW_2); 
             after(grammarAccess.getConditionalRuleTypeAccess().getMustKeyword_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__7__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__8"
    // InternalArchcnl.g:1816:1: rule__ConditionalRuleType__Group__8 : rule__ConditionalRuleType__Group__8__Impl rule__ConditionalRuleType__Group__9 ;
    public final void rule__ConditionalRuleType__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1820:1: ( rule__ConditionalRuleType__Group__8__Impl rule__ConditionalRuleType__Group__9 )
            // InternalArchcnl.g:1821:2: rule__ConditionalRuleType__Group__8__Impl rule__ConditionalRuleType__Group__9
            {
            pushFollow(FOLLOW_14);
            rule__ConditionalRuleType__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__8"


    // $ANTLR start "rule__ConditionalRuleType__Group__8__Impl"
    // InternalArchcnl.g:1828:1: rule__ConditionalRuleType__Group__8__Impl : ( ( rule__ConditionalRuleType__Relation2Assignment_8 ) ) ;
    public final void rule__ConditionalRuleType__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1832:1: ( ( ( rule__ConditionalRuleType__Relation2Assignment_8 ) ) )
            // InternalArchcnl.g:1833:1: ( ( rule__ConditionalRuleType__Relation2Assignment_8 ) )
            {
            // InternalArchcnl.g:1833:1: ( ( rule__ConditionalRuleType__Relation2Assignment_8 ) )
            // InternalArchcnl.g:1834:2: ( rule__ConditionalRuleType__Relation2Assignment_8 )
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getRelation2Assignment_8()); 
            // InternalArchcnl.g:1835:2: ( rule__ConditionalRuleType__Relation2Assignment_8 )
            // InternalArchcnl.g:1835:3: rule__ConditionalRuleType__Relation2Assignment_8
            {
            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Relation2Assignment_8();

            state._fsp--;


            }

             after(grammarAccess.getConditionalRuleTypeAccess().getRelation2Assignment_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__8__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__9"
    // InternalArchcnl.g:1843:1: rule__ConditionalRuleType__Group__9 : rule__ConditionalRuleType__Group__9__Impl rule__ConditionalRuleType__Group__10 ;
    public final void rule__ConditionalRuleType__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1847:1: ( rule__ConditionalRuleType__Group__9__Impl rule__ConditionalRuleType__Group__10 )
            // InternalArchcnl.g:1848:2: rule__ConditionalRuleType__Group__9__Impl rule__ConditionalRuleType__Group__10
            {
            pushFollow(FOLLOW_4);
            rule__ConditionalRuleType__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__9"


    // $ANTLR start "rule__ConditionalRuleType__Group__9__Impl"
    // InternalArchcnl.g:1855:1: rule__ConditionalRuleType__Group__9__Impl : ( 'this' ) ;
    public final void rule__ConditionalRuleType__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1859:1: ( ( 'this' ) )
            // InternalArchcnl.g:1860:1: ( 'this' )
            {
            // InternalArchcnl.g:1860:1: ( 'this' )
            // InternalArchcnl.g:1861:2: 'this'
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getThisKeyword_9()); 
            match(input,29,FOLLOW_2); 
             after(grammarAccess.getConditionalRuleTypeAccess().getThisKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__9__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__10"
    // InternalArchcnl.g:1870:1: rule__ConditionalRuleType__Group__10 : rule__ConditionalRuleType__Group__10__Impl rule__ConditionalRuleType__Group__11 ;
    public final void rule__ConditionalRuleType__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1874:1: ( rule__ConditionalRuleType__Group__10__Impl rule__ConditionalRuleType__Group__11 )
            // InternalArchcnl.g:1875:2: rule__ConditionalRuleType__Group__10__Impl rule__ConditionalRuleType__Group__11
            {
            pushFollow(FOLLOW_6);
            rule__ConditionalRuleType__Group__10__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__11();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__10"


    // $ANTLR start "rule__ConditionalRuleType__Group__10__Impl"
    // InternalArchcnl.g:1882:1: rule__ConditionalRuleType__Group__10__Impl : ( ( rule__ConditionalRuleType__Object2Assignment_10 ) ) ;
    public final void rule__ConditionalRuleType__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1886:1: ( ( ( rule__ConditionalRuleType__Object2Assignment_10 ) ) )
            // InternalArchcnl.g:1887:1: ( ( rule__ConditionalRuleType__Object2Assignment_10 ) )
            {
            // InternalArchcnl.g:1887:1: ( ( rule__ConditionalRuleType__Object2Assignment_10 ) )
            // InternalArchcnl.g:1888:2: ( rule__ConditionalRuleType__Object2Assignment_10 )
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getObject2Assignment_10()); 
            // InternalArchcnl.g:1889:2: ( rule__ConditionalRuleType__Object2Assignment_10 )
            // InternalArchcnl.g:1889:3: rule__ConditionalRuleType__Object2Assignment_10
            {
            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Object2Assignment_10();

            state._fsp--;


            }

             after(grammarAccess.getConditionalRuleTypeAccess().getObject2Assignment_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__10__Impl"


    // $ANTLR start "rule__ConditionalRuleType__Group__11"
    // InternalArchcnl.g:1897:1: rule__ConditionalRuleType__Group__11 : rule__ConditionalRuleType__Group__11__Impl ;
    public final void rule__ConditionalRuleType__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1901:1: ( rule__ConditionalRuleType__Group__11__Impl )
            // InternalArchcnl.g:1902:2: rule__ConditionalRuleType__Group__11__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ConditionalRuleType__Group__11__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__11"


    // $ANTLR start "rule__ConditionalRuleType__Group__11__Impl"
    // InternalArchcnl.g:1908:1: rule__ConditionalRuleType__Group__11__Impl : ( '.' ) ;
    public final void rule__ConditionalRuleType__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1912:1: ( ( '.' ) )
            // InternalArchcnl.g:1913:1: ( '.' )
            {
            // InternalArchcnl.g:1913:1: ( '.' )
            // InternalArchcnl.g:1914:2: '.'
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getFullStopKeyword_11()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getConditionalRuleTypeAccess().getFullStopKeyword_11()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Group__11__Impl"


    // $ANTLR start "rule__OnlyCanRuleType__Group__0"
    // InternalArchcnl.g:1924:1: rule__OnlyCanRuleType__Group__0 : rule__OnlyCanRuleType__Group__0__Impl rule__OnlyCanRuleType__Group__1 ;
    public final void rule__OnlyCanRuleType__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1928:1: ( rule__OnlyCanRuleType__Group__0__Impl rule__OnlyCanRuleType__Group__1 )
            // InternalArchcnl.g:1929:2: rule__OnlyCanRuleType__Group__0__Impl rule__OnlyCanRuleType__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__OnlyCanRuleType__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OnlyCanRuleType__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__Group__0"


    // $ANTLR start "rule__OnlyCanRuleType__Group__0__Impl"
    // InternalArchcnl.g:1936:1: rule__OnlyCanRuleType__Group__0__Impl : ( ( rule__OnlyCanRuleType__StartAssignment_0 ) ) ;
    public final void rule__OnlyCanRuleType__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1940:1: ( ( ( rule__OnlyCanRuleType__StartAssignment_0 ) ) )
            // InternalArchcnl.g:1941:1: ( ( rule__OnlyCanRuleType__StartAssignment_0 ) )
            {
            // InternalArchcnl.g:1941:1: ( ( rule__OnlyCanRuleType__StartAssignment_0 ) )
            // InternalArchcnl.g:1942:2: ( rule__OnlyCanRuleType__StartAssignment_0 )
            {
             before(grammarAccess.getOnlyCanRuleTypeAccess().getStartAssignment_0()); 
            // InternalArchcnl.g:1943:2: ( rule__OnlyCanRuleType__StartAssignment_0 )
            // InternalArchcnl.g:1943:3: rule__OnlyCanRuleType__StartAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__OnlyCanRuleType__StartAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getOnlyCanRuleTypeAccess().getStartAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__Group__0__Impl"


    // $ANTLR start "rule__OnlyCanRuleType__Group__1"
    // InternalArchcnl.g:1951:1: rule__OnlyCanRuleType__Group__1 : rule__OnlyCanRuleType__Group__1__Impl rule__OnlyCanRuleType__Group__2 ;
    public final void rule__OnlyCanRuleType__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1955:1: ( rule__OnlyCanRuleType__Group__1__Impl rule__OnlyCanRuleType__Group__2 )
            // InternalArchcnl.g:1956:2: rule__OnlyCanRuleType__Group__1__Impl rule__OnlyCanRuleType__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__OnlyCanRuleType__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OnlyCanRuleType__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__Group__1"


    // $ANTLR start "rule__OnlyCanRuleType__Group__1__Impl"
    // InternalArchcnl.g:1963:1: rule__OnlyCanRuleType__Group__1__Impl : ( ( rule__OnlyCanRuleType__SubjectAssignment_1 ) ) ;
    public final void rule__OnlyCanRuleType__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1967:1: ( ( ( rule__OnlyCanRuleType__SubjectAssignment_1 ) ) )
            // InternalArchcnl.g:1968:1: ( ( rule__OnlyCanRuleType__SubjectAssignment_1 ) )
            {
            // InternalArchcnl.g:1968:1: ( ( rule__OnlyCanRuleType__SubjectAssignment_1 ) )
            // InternalArchcnl.g:1969:2: ( rule__OnlyCanRuleType__SubjectAssignment_1 )
            {
             before(grammarAccess.getOnlyCanRuleTypeAccess().getSubjectAssignment_1()); 
            // InternalArchcnl.g:1970:2: ( rule__OnlyCanRuleType__SubjectAssignment_1 )
            // InternalArchcnl.g:1970:3: rule__OnlyCanRuleType__SubjectAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__OnlyCanRuleType__SubjectAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getOnlyCanRuleTypeAccess().getSubjectAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__Group__1__Impl"


    // $ANTLR start "rule__OnlyCanRuleType__Group__2"
    // InternalArchcnl.g:1978:1: rule__OnlyCanRuleType__Group__2 : rule__OnlyCanRuleType__Group__2__Impl rule__OnlyCanRuleType__Group__3 ;
    public final void rule__OnlyCanRuleType__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1982:1: ( rule__OnlyCanRuleType__Group__2__Impl rule__OnlyCanRuleType__Group__3 )
            // InternalArchcnl.g:1983:2: rule__OnlyCanRuleType__Group__2__Impl rule__OnlyCanRuleType__Group__3
            {
            pushFollow(FOLLOW_8);
            rule__OnlyCanRuleType__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OnlyCanRuleType__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__Group__2"


    // $ANTLR start "rule__OnlyCanRuleType__Group__2__Impl"
    // InternalArchcnl.g:1990:1: rule__OnlyCanRuleType__Group__2__Impl : ( 'can' ) ;
    public final void rule__OnlyCanRuleType__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:1994:1: ( ( 'can' ) )
            // InternalArchcnl.g:1995:1: ( 'can' )
            {
            // InternalArchcnl.g:1995:1: ( 'can' )
            // InternalArchcnl.g:1996:2: 'can'
            {
             before(grammarAccess.getOnlyCanRuleTypeAccess().getCanKeyword_2()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getOnlyCanRuleTypeAccess().getCanKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__Group__2__Impl"


    // $ANTLR start "rule__OnlyCanRuleType__Group__3"
    // InternalArchcnl.g:2005:1: rule__OnlyCanRuleType__Group__3 : rule__OnlyCanRuleType__Group__3__Impl rule__OnlyCanRuleType__Group__4 ;
    public final void rule__OnlyCanRuleType__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2009:1: ( rule__OnlyCanRuleType__Group__3__Impl rule__OnlyCanRuleType__Group__4 )
            // InternalArchcnl.g:2010:2: rule__OnlyCanRuleType__Group__3__Impl rule__OnlyCanRuleType__Group__4
            {
            pushFollow(FOLLOW_6);
            rule__OnlyCanRuleType__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OnlyCanRuleType__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__Group__3"


    // $ANTLR start "rule__OnlyCanRuleType__Group__3__Impl"
    // InternalArchcnl.g:2017:1: rule__OnlyCanRuleType__Group__3__Impl : ( ( rule__OnlyCanRuleType__ObjectAssignment_3 ) ) ;
    public final void rule__OnlyCanRuleType__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2021:1: ( ( ( rule__OnlyCanRuleType__ObjectAssignment_3 ) ) )
            // InternalArchcnl.g:2022:1: ( ( rule__OnlyCanRuleType__ObjectAssignment_3 ) )
            {
            // InternalArchcnl.g:2022:1: ( ( rule__OnlyCanRuleType__ObjectAssignment_3 ) )
            // InternalArchcnl.g:2023:2: ( rule__OnlyCanRuleType__ObjectAssignment_3 )
            {
             before(grammarAccess.getOnlyCanRuleTypeAccess().getObjectAssignment_3()); 
            // InternalArchcnl.g:2024:2: ( rule__OnlyCanRuleType__ObjectAssignment_3 )
            // InternalArchcnl.g:2024:3: rule__OnlyCanRuleType__ObjectAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__OnlyCanRuleType__ObjectAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getOnlyCanRuleTypeAccess().getObjectAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__Group__3__Impl"


    // $ANTLR start "rule__OnlyCanRuleType__Group__4"
    // InternalArchcnl.g:2032:1: rule__OnlyCanRuleType__Group__4 : rule__OnlyCanRuleType__Group__4__Impl ;
    public final void rule__OnlyCanRuleType__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2036:1: ( rule__OnlyCanRuleType__Group__4__Impl )
            // InternalArchcnl.g:2037:2: rule__OnlyCanRuleType__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OnlyCanRuleType__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__Group__4"


    // $ANTLR start "rule__OnlyCanRuleType__Group__4__Impl"
    // InternalArchcnl.g:2043:1: rule__OnlyCanRuleType__Group__4__Impl : ( '.' ) ;
    public final void rule__OnlyCanRuleType__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2047:1: ( ( '.' ) )
            // InternalArchcnl.g:2048:1: ( '.' )
            {
            // InternalArchcnl.g:2048:1: ( '.' )
            // InternalArchcnl.g:2049:2: '.'
            {
             before(grammarAccess.getOnlyCanRuleTypeAccess().getFullStopKeyword_4()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getOnlyCanRuleTypeAccess().getFullStopKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__Group__4__Impl"


    // $ANTLR start "rule__SubConceptRuleType__Group__0"
    // InternalArchcnl.g:2059:1: rule__SubConceptRuleType__Group__0 : rule__SubConceptRuleType__Group__0__Impl rule__SubConceptRuleType__Group__1 ;
    public final void rule__SubConceptRuleType__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2063:1: ( rule__SubConceptRuleType__Group__0__Impl rule__SubConceptRuleType__Group__1 )
            // InternalArchcnl.g:2064:2: rule__SubConceptRuleType__Group__0__Impl rule__SubConceptRuleType__Group__1
            {
            pushFollow(FOLLOW_15);
            rule__SubConceptRuleType__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SubConceptRuleType__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SubConceptRuleType__Group__0"


    // $ANTLR start "rule__SubConceptRuleType__Group__0__Impl"
    // InternalArchcnl.g:2071:1: rule__SubConceptRuleType__Group__0__Impl : ( ( rule__SubConceptRuleType__ModifierAssignment_0 ) ) ;
    public final void rule__SubConceptRuleType__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2075:1: ( ( ( rule__SubConceptRuleType__ModifierAssignment_0 ) ) )
            // InternalArchcnl.g:2076:1: ( ( rule__SubConceptRuleType__ModifierAssignment_0 ) )
            {
            // InternalArchcnl.g:2076:1: ( ( rule__SubConceptRuleType__ModifierAssignment_0 ) )
            // InternalArchcnl.g:2077:2: ( rule__SubConceptRuleType__ModifierAssignment_0 )
            {
             before(grammarAccess.getSubConceptRuleTypeAccess().getModifierAssignment_0()); 
            // InternalArchcnl.g:2078:2: ( rule__SubConceptRuleType__ModifierAssignment_0 )
            // InternalArchcnl.g:2078:3: rule__SubConceptRuleType__ModifierAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__SubConceptRuleType__ModifierAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getSubConceptRuleTypeAccess().getModifierAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SubConceptRuleType__Group__0__Impl"


    // $ANTLR start "rule__SubConceptRuleType__Group__1"
    // InternalArchcnl.g:2086:1: rule__SubConceptRuleType__Group__1 : rule__SubConceptRuleType__Group__1__Impl rule__SubConceptRuleType__Group__2 ;
    public final void rule__SubConceptRuleType__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2090:1: ( rule__SubConceptRuleType__Group__1__Impl rule__SubConceptRuleType__Group__2 )
            // InternalArchcnl.g:2091:2: rule__SubConceptRuleType__Group__1__Impl rule__SubConceptRuleType__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__SubConceptRuleType__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SubConceptRuleType__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SubConceptRuleType__Group__1"


    // $ANTLR start "rule__SubConceptRuleType__Group__1__Impl"
    // InternalArchcnl.g:2098:1: rule__SubConceptRuleType__Group__1__Impl : ( 'be' ) ;
    public final void rule__SubConceptRuleType__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2102:1: ( ( 'be' ) )
            // InternalArchcnl.g:2103:1: ( 'be' )
            {
            // InternalArchcnl.g:2103:1: ( 'be' )
            // InternalArchcnl.g:2104:2: 'be'
            {
             before(grammarAccess.getSubConceptRuleTypeAccess().getBeKeyword_1()); 
            match(input,30,FOLLOW_2); 
             after(grammarAccess.getSubConceptRuleTypeAccess().getBeKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SubConceptRuleType__Group__1__Impl"


    // $ANTLR start "rule__SubConceptRuleType__Group__2"
    // InternalArchcnl.g:2113:1: rule__SubConceptRuleType__Group__2 : rule__SubConceptRuleType__Group__2__Impl rule__SubConceptRuleType__Group__3 ;
    public final void rule__SubConceptRuleType__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2117:1: ( rule__SubConceptRuleType__Group__2__Impl rule__SubConceptRuleType__Group__3 )
            // InternalArchcnl.g:2118:2: rule__SubConceptRuleType__Group__2__Impl rule__SubConceptRuleType__Group__3
            {
            pushFollow(FOLLOW_6);
            rule__SubConceptRuleType__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SubConceptRuleType__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SubConceptRuleType__Group__2"


    // $ANTLR start "rule__SubConceptRuleType__Group__2__Impl"
    // InternalArchcnl.g:2125:1: rule__SubConceptRuleType__Group__2__Impl : ( ( rule__SubConceptRuleType__ObjectAssignment_2 ) ) ;
    public final void rule__SubConceptRuleType__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2129:1: ( ( ( rule__SubConceptRuleType__ObjectAssignment_2 ) ) )
            // InternalArchcnl.g:2130:1: ( ( rule__SubConceptRuleType__ObjectAssignment_2 ) )
            {
            // InternalArchcnl.g:2130:1: ( ( rule__SubConceptRuleType__ObjectAssignment_2 ) )
            // InternalArchcnl.g:2131:2: ( rule__SubConceptRuleType__ObjectAssignment_2 )
            {
             before(grammarAccess.getSubConceptRuleTypeAccess().getObjectAssignment_2()); 
            // InternalArchcnl.g:2132:2: ( rule__SubConceptRuleType__ObjectAssignment_2 )
            // InternalArchcnl.g:2132:3: rule__SubConceptRuleType__ObjectAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__SubConceptRuleType__ObjectAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getSubConceptRuleTypeAccess().getObjectAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SubConceptRuleType__Group__2__Impl"


    // $ANTLR start "rule__SubConceptRuleType__Group__3"
    // InternalArchcnl.g:2140:1: rule__SubConceptRuleType__Group__3 : rule__SubConceptRuleType__Group__3__Impl ;
    public final void rule__SubConceptRuleType__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2144:1: ( rule__SubConceptRuleType__Group__3__Impl )
            // InternalArchcnl.g:2145:2: rule__SubConceptRuleType__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SubConceptRuleType__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SubConceptRuleType__Group__3"


    // $ANTLR start "rule__SubConceptRuleType__Group__3__Impl"
    // InternalArchcnl.g:2151:1: rule__SubConceptRuleType__Group__3__Impl : ( '.' ) ;
    public final void rule__SubConceptRuleType__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2155:1: ( ( '.' ) )
            // InternalArchcnl.g:2156:1: ( '.' )
            {
            // InternalArchcnl.g:2156:1: ( '.' )
            // InternalArchcnl.g:2157:2: '.'
            {
             before(grammarAccess.getSubConceptRuleTypeAccess().getFullStopKeyword_3()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getSubConceptRuleTypeAccess().getFullStopKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SubConceptRuleType__Group__3__Impl"


    // $ANTLR start "rule__MustRuleType__Group__0"
    // InternalArchcnl.g:2167:1: rule__MustRuleType__Group__0 : rule__MustRuleType__Group__0__Impl rule__MustRuleType__Group__1 ;
    public final void rule__MustRuleType__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2171:1: ( rule__MustRuleType__Group__0__Impl rule__MustRuleType__Group__1 )
            // InternalArchcnl.g:2172:2: rule__MustRuleType__Group__0__Impl rule__MustRuleType__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__MustRuleType__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MustRuleType__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MustRuleType__Group__0"


    // $ANTLR start "rule__MustRuleType__Group__0__Impl"
    // InternalArchcnl.g:2179:1: rule__MustRuleType__Group__0__Impl : ( ( rule__MustRuleType__ModifierAssignment_0 ) ) ;
    public final void rule__MustRuleType__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2183:1: ( ( ( rule__MustRuleType__ModifierAssignment_0 ) ) )
            // InternalArchcnl.g:2184:1: ( ( rule__MustRuleType__ModifierAssignment_0 ) )
            {
            // InternalArchcnl.g:2184:1: ( ( rule__MustRuleType__ModifierAssignment_0 ) )
            // InternalArchcnl.g:2185:2: ( rule__MustRuleType__ModifierAssignment_0 )
            {
             before(grammarAccess.getMustRuleTypeAccess().getModifierAssignment_0()); 
            // InternalArchcnl.g:2186:2: ( rule__MustRuleType__ModifierAssignment_0 )
            // InternalArchcnl.g:2186:3: rule__MustRuleType__ModifierAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__MustRuleType__ModifierAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getMustRuleTypeAccess().getModifierAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MustRuleType__Group__0__Impl"


    // $ANTLR start "rule__MustRuleType__Group__1"
    // InternalArchcnl.g:2194:1: rule__MustRuleType__Group__1 : rule__MustRuleType__Group__1__Impl rule__MustRuleType__Group__2 ;
    public final void rule__MustRuleType__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2198:1: ( rule__MustRuleType__Group__1__Impl rule__MustRuleType__Group__2 )
            // InternalArchcnl.g:2199:2: rule__MustRuleType__Group__1__Impl rule__MustRuleType__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__MustRuleType__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__MustRuleType__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MustRuleType__Group__1"


    // $ANTLR start "rule__MustRuleType__Group__1__Impl"
    // InternalArchcnl.g:2206:1: rule__MustRuleType__Group__1__Impl : ( ( rule__MustRuleType__ObjectAssignment_1 ) ) ;
    public final void rule__MustRuleType__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2210:1: ( ( ( rule__MustRuleType__ObjectAssignment_1 ) ) )
            // InternalArchcnl.g:2211:1: ( ( rule__MustRuleType__ObjectAssignment_1 ) )
            {
            // InternalArchcnl.g:2211:1: ( ( rule__MustRuleType__ObjectAssignment_1 ) )
            // InternalArchcnl.g:2212:2: ( rule__MustRuleType__ObjectAssignment_1 )
            {
             before(grammarAccess.getMustRuleTypeAccess().getObjectAssignment_1()); 
            // InternalArchcnl.g:2213:2: ( rule__MustRuleType__ObjectAssignment_1 )
            // InternalArchcnl.g:2213:3: rule__MustRuleType__ObjectAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__MustRuleType__ObjectAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getMustRuleTypeAccess().getObjectAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MustRuleType__Group__1__Impl"


    // $ANTLR start "rule__MustRuleType__Group__2"
    // InternalArchcnl.g:2221:1: rule__MustRuleType__Group__2 : rule__MustRuleType__Group__2__Impl ;
    public final void rule__MustRuleType__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2225:1: ( rule__MustRuleType__Group__2__Impl )
            // InternalArchcnl.g:2226:2: rule__MustRuleType__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__MustRuleType__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MustRuleType__Group__2"


    // $ANTLR start "rule__MustRuleType__Group__2__Impl"
    // InternalArchcnl.g:2232:1: rule__MustRuleType__Group__2__Impl : ( '.' ) ;
    public final void rule__MustRuleType__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2236:1: ( ( '.' ) )
            // InternalArchcnl.g:2237:1: ( '.' )
            {
            // InternalArchcnl.g:2237:1: ( '.' )
            // InternalArchcnl.g:2238:2: '.'
            {
             before(grammarAccess.getMustRuleTypeAccess().getFullStopKeyword_2()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getMustRuleTypeAccess().getFullStopKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MustRuleType__Group__2__Impl"


    // $ANTLR start "rule__Object__Group_1__0"
    // InternalArchcnl.g:2248:1: rule__Object__Group_1__0 : rule__Object__Group_1__0__Impl rule__Object__Group_1__1 ;
    public final void rule__Object__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2252:1: ( rule__Object__Group_1__0__Impl rule__Object__Group_1__1 )
            // InternalArchcnl.g:2253:2: rule__Object__Group_1__0__Impl rule__Object__Group_1__1
            {
            pushFollow(FOLLOW_16);
            rule__Object__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Object__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Object__Group_1__0"


    // $ANTLR start "rule__Object__Group_1__0__Impl"
    // InternalArchcnl.g:2260:1: rule__Object__Group_1__0__Impl : ( ( rule__Object__ExpressionAssignment_1_0 ) ) ;
    public final void rule__Object__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2264:1: ( ( ( rule__Object__ExpressionAssignment_1_0 ) ) )
            // InternalArchcnl.g:2265:1: ( ( rule__Object__ExpressionAssignment_1_0 ) )
            {
            // InternalArchcnl.g:2265:1: ( ( rule__Object__ExpressionAssignment_1_0 ) )
            // InternalArchcnl.g:2266:2: ( rule__Object__ExpressionAssignment_1_0 )
            {
             before(grammarAccess.getObjectAccess().getExpressionAssignment_1_0()); 
            // InternalArchcnl.g:2267:2: ( rule__Object__ExpressionAssignment_1_0 )
            // InternalArchcnl.g:2267:3: rule__Object__ExpressionAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Object__ExpressionAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getObjectAccess().getExpressionAssignment_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Object__Group_1__0__Impl"


    // $ANTLR start "rule__Object__Group_1__1"
    // InternalArchcnl.g:2275:1: rule__Object__Group_1__1 : rule__Object__Group_1__1__Impl ;
    public final void rule__Object__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2279:1: ( rule__Object__Group_1__1__Impl )
            // InternalArchcnl.g:2280:2: rule__Object__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Object__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Object__Group_1__1"


    // $ANTLR start "rule__Object__Group_1__1__Impl"
    // InternalArchcnl.g:2286:1: rule__Object__Group_1__1__Impl : ( ( rule__Object__Alternatives_1_1 )* ) ;
    public final void rule__Object__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2290:1: ( ( ( rule__Object__Alternatives_1_1 )* ) )
            // InternalArchcnl.g:2291:1: ( ( rule__Object__Alternatives_1_1 )* )
            {
            // InternalArchcnl.g:2291:1: ( ( rule__Object__Alternatives_1_1 )* )
            // InternalArchcnl.g:2292:2: ( rule__Object__Alternatives_1_1 )*
            {
             before(grammarAccess.getObjectAccess().getAlternatives_1_1()); 
            // InternalArchcnl.g:2293:2: ( rule__Object__Alternatives_1_1 )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>=31 && LA19_0<=32)) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalArchcnl.g:2293:3: rule__Object__Alternatives_1_1
            	    {
            	    pushFollow(FOLLOW_17);
            	    rule__Object__Alternatives_1_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

             after(grammarAccess.getObjectAccess().getAlternatives_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Object__Group_1__1__Impl"


    // $ANTLR start "rule__CanOnlyRuleType__Group__0"
    // InternalArchcnl.g:2302:1: rule__CanOnlyRuleType__Group__0 : rule__CanOnlyRuleType__Group__0__Impl rule__CanOnlyRuleType__Group__1 ;
    public final void rule__CanOnlyRuleType__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2306:1: ( rule__CanOnlyRuleType__Group__0__Impl rule__CanOnlyRuleType__Group__1 )
            // InternalArchcnl.g:2307:2: rule__CanOnlyRuleType__Group__0__Impl rule__CanOnlyRuleType__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__CanOnlyRuleType__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__CanOnlyRuleType__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CanOnlyRuleType__Group__0"


    // $ANTLR start "rule__CanOnlyRuleType__Group__0__Impl"
    // InternalArchcnl.g:2314:1: rule__CanOnlyRuleType__Group__0__Impl : ( ( rule__CanOnlyRuleType__ModifierAssignment_0 ) ) ;
    public final void rule__CanOnlyRuleType__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2318:1: ( ( ( rule__CanOnlyRuleType__ModifierAssignment_0 ) ) )
            // InternalArchcnl.g:2319:1: ( ( rule__CanOnlyRuleType__ModifierAssignment_0 ) )
            {
            // InternalArchcnl.g:2319:1: ( ( rule__CanOnlyRuleType__ModifierAssignment_0 ) )
            // InternalArchcnl.g:2320:2: ( rule__CanOnlyRuleType__ModifierAssignment_0 )
            {
             before(grammarAccess.getCanOnlyRuleTypeAccess().getModifierAssignment_0()); 
            // InternalArchcnl.g:2321:2: ( rule__CanOnlyRuleType__ModifierAssignment_0 )
            // InternalArchcnl.g:2321:3: rule__CanOnlyRuleType__ModifierAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__CanOnlyRuleType__ModifierAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getCanOnlyRuleTypeAccess().getModifierAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CanOnlyRuleType__Group__0__Impl"


    // $ANTLR start "rule__CanOnlyRuleType__Group__1"
    // InternalArchcnl.g:2329:1: rule__CanOnlyRuleType__Group__1 : rule__CanOnlyRuleType__Group__1__Impl rule__CanOnlyRuleType__Group__2 ;
    public final void rule__CanOnlyRuleType__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2333:1: ( rule__CanOnlyRuleType__Group__1__Impl rule__CanOnlyRuleType__Group__2 )
            // InternalArchcnl.g:2334:2: rule__CanOnlyRuleType__Group__1__Impl rule__CanOnlyRuleType__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__CanOnlyRuleType__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__CanOnlyRuleType__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CanOnlyRuleType__Group__1"


    // $ANTLR start "rule__CanOnlyRuleType__Group__1__Impl"
    // InternalArchcnl.g:2341:1: rule__CanOnlyRuleType__Group__1__Impl : ( ( rule__CanOnlyRuleType__ObjectAssignment_1 ) ) ;
    public final void rule__CanOnlyRuleType__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2345:1: ( ( ( rule__CanOnlyRuleType__ObjectAssignment_1 ) ) )
            // InternalArchcnl.g:2346:1: ( ( rule__CanOnlyRuleType__ObjectAssignment_1 ) )
            {
            // InternalArchcnl.g:2346:1: ( ( rule__CanOnlyRuleType__ObjectAssignment_1 ) )
            // InternalArchcnl.g:2347:2: ( rule__CanOnlyRuleType__ObjectAssignment_1 )
            {
             before(grammarAccess.getCanOnlyRuleTypeAccess().getObjectAssignment_1()); 
            // InternalArchcnl.g:2348:2: ( rule__CanOnlyRuleType__ObjectAssignment_1 )
            // InternalArchcnl.g:2348:3: rule__CanOnlyRuleType__ObjectAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__CanOnlyRuleType__ObjectAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getCanOnlyRuleTypeAccess().getObjectAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CanOnlyRuleType__Group__1__Impl"


    // $ANTLR start "rule__CanOnlyRuleType__Group__2"
    // InternalArchcnl.g:2356:1: rule__CanOnlyRuleType__Group__2 : rule__CanOnlyRuleType__Group__2__Impl ;
    public final void rule__CanOnlyRuleType__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2360:1: ( rule__CanOnlyRuleType__Group__2__Impl )
            // InternalArchcnl.g:2361:2: rule__CanOnlyRuleType__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__CanOnlyRuleType__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CanOnlyRuleType__Group__2"


    // $ANTLR start "rule__CanOnlyRuleType__Group__2__Impl"
    // InternalArchcnl.g:2367:1: rule__CanOnlyRuleType__Group__2__Impl : ( '.' ) ;
    public final void rule__CanOnlyRuleType__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2371:1: ( ( '.' ) )
            // InternalArchcnl.g:2372:1: ( '.' )
            {
            // InternalArchcnl.g:2372:1: ( '.' )
            // InternalArchcnl.g:2373:2: '.'
            {
             before(grammarAccess.getCanOnlyRuleTypeAccess().getFullStopKeyword_2()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getCanOnlyRuleTypeAccess().getFullStopKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CanOnlyRuleType__Group__2__Impl"


    // $ANTLR start "rule__CardinalityRuleType__Group__0"
    // InternalArchcnl.g:2383:1: rule__CardinalityRuleType__Group__0 : rule__CardinalityRuleType__Group__0__Impl rule__CardinalityRuleType__Group__1 ;
    public final void rule__CardinalityRuleType__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2387:1: ( rule__CardinalityRuleType__Group__0__Impl rule__CardinalityRuleType__Group__1 )
            // InternalArchcnl.g:2388:2: rule__CardinalityRuleType__Group__0__Impl rule__CardinalityRuleType__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__CardinalityRuleType__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__CardinalityRuleType__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CardinalityRuleType__Group__0"


    // $ANTLR start "rule__CardinalityRuleType__Group__0__Impl"
    // InternalArchcnl.g:2395:1: rule__CardinalityRuleType__Group__0__Impl : ( ( rule__CardinalityRuleType__ModiferAssignment_0 ) ) ;
    public final void rule__CardinalityRuleType__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2399:1: ( ( ( rule__CardinalityRuleType__ModiferAssignment_0 ) ) )
            // InternalArchcnl.g:2400:1: ( ( rule__CardinalityRuleType__ModiferAssignment_0 ) )
            {
            // InternalArchcnl.g:2400:1: ( ( rule__CardinalityRuleType__ModiferAssignment_0 ) )
            // InternalArchcnl.g:2401:2: ( rule__CardinalityRuleType__ModiferAssignment_0 )
            {
             before(grammarAccess.getCardinalityRuleTypeAccess().getModiferAssignment_0()); 
            // InternalArchcnl.g:2402:2: ( rule__CardinalityRuleType__ModiferAssignment_0 )
            // InternalArchcnl.g:2402:3: rule__CardinalityRuleType__ModiferAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__CardinalityRuleType__ModiferAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getCardinalityRuleTypeAccess().getModiferAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CardinalityRuleType__Group__0__Impl"


    // $ANTLR start "rule__CardinalityRuleType__Group__1"
    // InternalArchcnl.g:2410:1: rule__CardinalityRuleType__Group__1 : rule__CardinalityRuleType__Group__1__Impl rule__CardinalityRuleType__Group__2 ;
    public final void rule__CardinalityRuleType__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2414:1: ( rule__CardinalityRuleType__Group__1__Impl rule__CardinalityRuleType__Group__2 )
            // InternalArchcnl.g:2415:2: rule__CardinalityRuleType__Group__1__Impl rule__CardinalityRuleType__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__CardinalityRuleType__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__CardinalityRuleType__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CardinalityRuleType__Group__1"


    // $ANTLR start "rule__CardinalityRuleType__Group__1__Impl"
    // InternalArchcnl.g:2422:1: rule__CardinalityRuleType__Group__1__Impl : ( ( rule__CardinalityRuleType__ObjectAssignment_1 ) ) ;
    public final void rule__CardinalityRuleType__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2426:1: ( ( ( rule__CardinalityRuleType__ObjectAssignment_1 ) ) )
            // InternalArchcnl.g:2427:1: ( ( rule__CardinalityRuleType__ObjectAssignment_1 ) )
            {
            // InternalArchcnl.g:2427:1: ( ( rule__CardinalityRuleType__ObjectAssignment_1 ) )
            // InternalArchcnl.g:2428:2: ( rule__CardinalityRuleType__ObjectAssignment_1 )
            {
             before(grammarAccess.getCardinalityRuleTypeAccess().getObjectAssignment_1()); 
            // InternalArchcnl.g:2429:2: ( rule__CardinalityRuleType__ObjectAssignment_1 )
            // InternalArchcnl.g:2429:3: rule__CardinalityRuleType__ObjectAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__CardinalityRuleType__ObjectAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getCardinalityRuleTypeAccess().getObjectAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CardinalityRuleType__Group__1__Impl"


    // $ANTLR start "rule__CardinalityRuleType__Group__2"
    // InternalArchcnl.g:2437:1: rule__CardinalityRuleType__Group__2 : rule__CardinalityRuleType__Group__2__Impl ;
    public final void rule__CardinalityRuleType__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2441:1: ( rule__CardinalityRuleType__Group__2__Impl )
            // InternalArchcnl.g:2442:2: rule__CardinalityRuleType__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__CardinalityRuleType__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CardinalityRuleType__Group__2"


    // $ANTLR start "rule__CardinalityRuleType__Group__2__Impl"
    // InternalArchcnl.g:2448:1: rule__CardinalityRuleType__Group__2__Impl : ( '.' ) ;
    public final void rule__CardinalityRuleType__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2452:1: ( ( '.' ) )
            // InternalArchcnl.g:2453:1: ( '.' )
            {
            // InternalArchcnl.g:2453:1: ( '.' )
            // InternalArchcnl.g:2454:2: '.'
            {
             before(grammarAccess.getCardinalityRuleTypeAccess().getFullStopKeyword_2()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getCardinalityRuleTypeAccess().getFullStopKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CardinalityRuleType__Group__2__Impl"


    // $ANTLR start "rule__ObjectConceptExpression__Group_0__0"
    // InternalArchcnl.g:2464:1: rule__ObjectConceptExpression__Group_0__0 : rule__ObjectConceptExpression__Group_0__0__Impl rule__ObjectConceptExpression__Group_0__1 ;
    public final void rule__ObjectConceptExpression__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2468:1: ( rule__ObjectConceptExpression__Group_0__0__Impl rule__ObjectConceptExpression__Group_0__1 )
            // InternalArchcnl.g:2469:2: rule__ObjectConceptExpression__Group_0__0__Impl rule__ObjectConceptExpression__Group_0__1
            {
            pushFollow(FOLLOW_18);
            rule__ObjectConceptExpression__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_0__0"


    // $ANTLR start "rule__ObjectConceptExpression__Group_0__0__Impl"
    // InternalArchcnl.g:2476:1: rule__ObjectConceptExpression__Group_0__0__Impl : ( ( rule__ObjectConceptExpression__RelationAssignment_0_0 ) ) ;
    public final void rule__ObjectConceptExpression__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2480:1: ( ( ( rule__ObjectConceptExpression__RelationAssignment_0_0 ) ) )
            // InternalArchcnl.g:2481:1: ( ( rule__ObjectConceptExpression__RelationAssignment_0_0 ) )
            {
            // InternalArchcnl.g:2481:1: ( ( rule__ObjectConceptExpression__RelationAssignment_0_0 ) )
            // InternalArchcnl.g:2482:2: ( rule__ObjectConceptExpression__RelationAssignment_0_0 )
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getRelationAssignment_0_0()); 
            // InternalArchcnl.g:2483:2: ( rule__ObjectConceptExpression__RelationAssignment_0_0 )
            // InternalArchcnl.g:2483:3: rule__ObjectConceptExpression__RelationAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__RelationAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getObjectConceptExpressionAccess().getRelationAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_0__0__Impl"


    // $ANTLR start "rule__ObjectConceptExpression__Group_0__1"
    // InternalArchcnl.g:2491:1: rule__ObjectConceptExpression__Group_0__1 : rule__ObjectConceptExpression__Group_0__1__Impl rule__ObjectConceptExpression__Group_0__2 ;
    public final void rule__ObjectConceptExpression__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2495:1: ( rule__ObjectConceptExpression__Group_0__1__Impl rule__ObjectConceptExpression__Group_0__2 )
            // InternalArchcnl.g:2496:2: rule__ObjectConceptExpression__Group_0__1__Impl rule__ObjectConceptExpression__Group_0__2
            {
            pushFollow(FOLLOW_18);
            rule__ObjectConceptExpression__Group_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__Group_0__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_0__1"


    // $ANTLR start "rule__ObjectConceptExpression__Group_0__1__Impl"
    // InternalArchcnl.g:2503:1: rule__ObjectConceptExpression__Group_0__1__Impl : ( ( rule__ObjectConceptExpression__Group_0_1__0 )? ) ;
    public final void rule__ObjectConceptExpression__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2507:1: ( ( ( rule__ObjectConceptExpression__Group_0_1__0 )? ) )
            // InternalArchcnl.g:2508:1: ( ( rule__ObjectConceptExpression__Group_0_1__0 )? )
            {
            // InternalArchcnl.g:2508:1: ( ( rule__ObjectConceptExpression__Group_0_1__0 )? )
            // InternalArchcnl.g:2509:2: ( rule__ObjectConceptExpression__Group_0_1__0 )?
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getGroup_0_1()); 
            // InternalArchcnl.g:2510:2: ( rule__ObjectConceptExpression__Group_0_1__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( ((LA20_0>=14 && LA20_0<=16)) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalArchcnl.g:2510:3: rule__ObjectConceptExpression__Group_0_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ObjectConceptExpression__Group_0_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getObjectConceptExpressionAccess().getGroup_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_0__1__Impl"


    // $ANTLR start "rule__ObjectConceptExpression__Group_0__2"
    // InternalArchcnl.g:2518:1: rule__ObjectConceptExpression__Group_0__2 : rule__ObjectConceptExpression__Group_0__2__Impl ;
    public final void rule__ObjectConceptExpression__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2522:1: ( rule__ObjectConceptExpression__Group_0__2__Impl )
            // InternalArchcnl.g:2523:2: rule__ObjectConceptExpression__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__Group_0__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_0__2"


    // $ANTLR start "rule__ObjectConceptExpression__Group_0__2__Impl"
    // InternalArchcnl.g:2529:1: rule__ObjectConceptExpression__Group_0__2__Impl : ( ( rule__ObjectConceptExpression__ConceptAssignment_0_2 ) ) ;
    public final void rule__ObjectConceptExpression__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2533:1: ( ( ( rule__ObjectConceptExpression__ConceptAssignment_0_2 ) ) )
            // InternalArchcnl.g:2534:1: ( ( rule__ObjectConceptExpression__ConceptAssignment_0_2 ) )
            {
            // InternalArchcnl.g:2534:1: ( ( rule__ObjectConceptExpression__ConceptAssignment_0_2 ) )
            // InternalArchcnl.g:2535:2: ( rule__ObjectConceptExpression__ConceptAssignment_0_2 )
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getConceptAssignment_0_2()); 
            // InternalArchcnl.g:2536:2: ( rule__ObjectConceptExpression__ConceptAssignment_0_2 )
            // InternalArchcnl.g:2536:3: rule__ObjectConceptExpression__ConceptAssignment_0_2
            {
            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__ConceptAssignment_0_2();

            state._fsp--;


            }

             after(grammarAccess.getObjectConceptExpressionAccess().getConceptAssignment_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_0__2__Impl"


    // $ANTLR start "rule__ObjectConceptExpression__Group_0_1__0"
    // InternalArchcnl.g:2545:1: rule__ObjectConceptExpression__Group_0_1__0 : rule__ObjectConceptExpression__Group_0_1__0__Impl rule__ObjectConceptExpression__Group_0_1__1 ;
    public final void rule__ObjectConceptExpression__Group_0_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2549:1: ( rule__ObjectConceptExpression__Group_0_1__0__Impl rule__ObjectConceptExpression__Group_0_1__1 )
            // InternalArchcnl.g:2550:2: rule__ObjectConceptExpression__Group_0_1__0__Impl rule__ObjectConceptExpression__Group_0_1__1
            {
            pushFollow(FOLLOW_19);
            rule__ObjectConceptExpression__Group_0_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__Group_0_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_0_1__0"


    // $ANTLR start "rule__ObjectConceptExpression__Group_0_1__0__Impl"
    // InternalArchcnl.g:2557:1: rule__ObjectConceptExpression__Group_0_1__0__Impl : ( ( rule__ObjectConceptExpression__CardinalityAssignment_0_1_0 ) ) ;
    public final void rule__ObjectConceptExpression__Group_0_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2561:1: ( ( ( rule__ObjectConceptExpression__CardinalityAssignment_0_1_0 ) ) )
            // InternalArchcnl.g:2562:1: ( ( rule__ObjectConceptExpression__CardinalityAssignment_0_1_0 ) )
            {
            // InternalArchcnl.g:2562:1: ( ( rule__ObjectConceptExpression__CardinalityAssignment_0_1_0 ) )
            // InternalArchcnl.g:2563:2: ( rule__ObjectConceptExpression__CardinalityAssignment_0_1_0 )
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getCardinalityAssignment_0_1_0()); 
            // InternalArchcnl.g:2564:2: ( rule__ObjectConceptExpression__CardinalityAssignment_0_1_0 )
            // InternalArchcnl.g:2564:3: rule__ObjectConceptExpression__CardinalityAssignment_0_1_0
            {
            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__CardinalityAssignment_0_1_0();

            state._fsp--;


            }

             after(grammarAccess.getObjectConceptExpressionAccess().getCardinalityAssignment_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_0_1__0__Impl"


    // $ANTLR start "rule__ObjectConceptExpression__Group_0_1__1"
    // InternalArchcnl.g:2572:1: rule__ObjectConceptExpression__Group_0_1__1 : rule__ObjectConceptExpression__Group_0_1__1__Impl ;
    public final void rule__ObjectConceptExpression__Group_0_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2576:1: ( rule__ObjectConceptExpression__Group_0_1__1__Impl )
            // InternalArchcnl.g:2577:2: rule__ObjectConceptExpression__Group_0_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__Group_0_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_0_1__1"


    // $ANTLR start "rule__ObjectConceptExpression__Group_0_1__1__Impl"
    // InternalArchcnl.g:2583:1: rule__ObjectConceptExpression__Group_0_1__1__Impl : ( ( rule__ObjectConceptExpression__NumberAssignment_0_1_1 ) ) ;
    public final void rule__ObjectConceptExpression__Group_0_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2587:1: ( ( ( rule__ObjectConceptExpression__NumberAssignment_0_1_1 ) ) )
            // InternalArchcnl.g:2588:1: ( ( rule__ObjectConceptExpression__NumberAssignment_0_1_1 ) )
            {
            // InternalArchcnl.g:2588:1: ( ( rule__ObjectConceptExpression__NumberAssignment_0_1_1 ) )
            // InternalArchcnl.g:2589:2: ( rule__ObjectConceptExpression__NumberAssignment_0_1_1 )
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getNumberAssignment_0_1_1()); 
            // InternalArchcnl.g:2590:2: ( rule__ObjectConceptExpression__NumberAssignment_0_1_1 )
            // InternalArchcnl.g:2590:3: rule__ObjectConceptExpression__NumberAssignment_0_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__NumberAssignment_0_1_1();

            state._fsp--;


            }

             after(grammarAccess.getObjectConceptExpressionAccess().getNumberAssignment_0_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_0_1__1__Impl"


    // $ANTLR start "rule__ObjectConceptExpression__Group_1__0"
    // InternalArchcnl.g:2599:1: rule__ObjectConceptExpression__Group_1__0 : rule__ObjectConceptExpression__Group_1__0__Impl rule__ObjectConceptExpression__Group_1__1 ;
    public final void rule__ObjectConceptExpression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2603:1: ( rule__ObjectConceptExpression__Group_1__0__Impl rule__ObjectConceptExpression__Group_1__1 )
            // InternalArchcnl.g:2604:2: rule__ObjectConceptExpression__Group_1__0__Impl rule__ObjectConceptExpression__Group_1__1
            {
            pushFollow(FOLLOW_20);
            rule__ObjectConceptExpression__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_1__0"


    // $ANTLR start "rule__ObjectConceptExpression__Group_1__0__Impl"
    // InternalArchcnl.g:2611:1: rule__ObjectConceptExpression__Group_1__0__Impl : ( ( rule__ObjectConceptExpression__RelationAssignment_1_0 ) ) ;
    public final void rule__ObjectConceptExpression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2615:1: ( ( ( rule__ObjectConceptExpression__RelationAssignment_1_0 ) ) )
            // InternalArchcnl.g:2616:1: ( ( rule__ObjectConceptExpression__RelationAssignment_1_0 ) )
            {
            // InternalArchcnl.g:2616:1: ( ( rule__ObjectConceptExpression__RelationAssignment_1_0 ) )
            // InternalArchcnl.g:2617:2: ( rule__ObjectConceptExpression__RelationAssignment_1_0 )
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getRelationAssignment_1_0()); 
            // InternalArchcnl.g:2618:2: ( rule__ObjectConceptExpression__RelationAssignment_1_0 )
            // InternalArchcnl.g:2618:3: rule__ObjectConceptExpression__RelationAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__RelationAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getObjectConceptExpressionAccess().getRelationAssignment_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_1__0__Impl"


    // $ANTLR start "rule__ObjectConceptExpression__Group_1__1"
    // InternalArchcnl.g:2626:1: rule__ObjectConceptExpression__Group_1__1 : rule__ObjectConceptExpression__Group_1__1__Impl ;
    public final void rule__ObjectConceptExpression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2630:1: ( rule__ObjectConceptExpression__Group_1__1__Impl )
            // InternalArchcnl.g:2631:2: rule__ObjectConceptExpression__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_1__1"


    // $ANTLR start "rule__ObjectConceptExpression__Group_1__1__Impl"
    // InternalArchcnl.g:2637:1: rule__ObjectConceptExpression__Group_1__1__Impl : ( ( rule__ObjectConceptExpression__DataAssignment_1_1 ) ) ;
    public final void rule__ObjectConceptExpression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2641:1: ( ( ( rule__ObjectConceptExpression__DataAssignment_1_1 ) ) )
            // InternalArchcnl.g:2642:1: ( ( rule__ObjectConceptExpression__DataAssignment_1_1 ) )
            {
            // InternalArchcnl.g:2642:1: ( ( rule__ObjectConceptExpression__DataAssignment_1_1 ) )
            // InternalArchcnl.g:2643:2: ( rule__ObjectConceptExpression__DataAssignment_1_1 )
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getDataAssignment_1_1()); 
            // InternalArchcnl.g:2644:2: ( rule__ObjectConceptExpression__DataAssignment_1_1 )
            // InternalArchcnl.g:2644:3: rule__ObjectConceptExpression__DataAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__DataAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getObjectConceptExpressionAccess().getDataAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__Group_1__1__Impl"


    // $ANTLR start "rule__AndObjectConceptExpression__Group__0"
    // InternalArchcnl.g:2653:1: rule__AndObjectConceptExpression__Group__0 : rule__AndObjectConceptExpression__Group__0__Impl rule__AndObjectConceptExpression__Group__1 ;
    public final void rule__AndObjectConceptExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2657:1: ( rule__AndObjectConceptExpression__Group__0__Impl rule__AndObjectConceptExpression__Group__1 )
            // InternalArchcnl.g:2658:2: rule__AndObjectConceptExpression__Group__0__Impl rule__AndObjectConceptExpression__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__AndObjectConceptExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__AndObjectConceptExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndObjectConceptExpression__Group__0"


    // $ANTLR start "rule__AndObjectConceptExpression__Group__0__Impl"
    // InternalArchcnl.g:2665:1: rule__AndObjectConceptExpression__Group__0__Impl : ( 'and' ) ;
    public final void rule__AndObjectConceptExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2669:1: ( ( 'and' ) )
            // InternalArchcnl.g:2670:1: ( 'and' )
            {
            // InternalArchcnl.g:2670:1: ( 'and' )
            // InternalArchcnl.g:2671:2: 'and'
            {
             before(grammarAccess.getAndObjectConceptExpressionAccess().getAndKeyword_0()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getAndObjectConceptExpressionAccess().getAndKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndObjectConceptExpression__Group__0__Impl"


    // $ANTLR start "rule__AndObjectConceptExpression__Group__1"
    // InternalArchcnl.g:2680:1: rule__AndObjectConceptExpression__Group__1 : rule__AndObjectConceptExpression__Group__1__Impl ;
    public final void rule__AndObjectConceptExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2684:1: ( rule__AndObjectConceptExpression__Group__1__Impl )
            // InternalArchcnl.g:2685:2: rule__AndObjectConceptExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__AndObjectConceptExpression__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndObjectConceptExpression__Group__1"


    // $ANTLR start "rule__AndObjectConceptExpression__Group__1__Impl"
    // InternalArchcnl.g:2691:1: rule__AndObjectConceptExpression__Group__1__Impl : ( ( rule__AndObjectConceptExpression__ExpressionAssignment_1 ) ) ;
    public final void rule__AndObjectConceptExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2695:1: ( ( ( rule__AndObjectConceptExpression__ExpressionAssignment_1 ) ) )
            // InternalArchcnl.g:2696:1: ( ( rule__AndObjectConceptExpression__ExpressionAssignment_1 ) )
            {
            // InternalArchcnl.g:2696:1: ( ( rule__AndObjectConceptExpression__ExpressionAssignment_1 ) )
            // InternalArchcnl.g:2697:2: ( rule__AndObjectConceptExpression__ExpressionAssignment_1 )
            {
             before(grammarAccess.getAndObjectConceptExpressionAccess().getExpressionAssignment_1()); 
            // InternalArchcnl.g:2698:2: ( rule__AndObjectConceptExpression__ExpressionAssignment_1 )
            // InternalArchcnl.g:2698:3: rule__AndObjectConceptExpression__ExpressionAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__AndObjectConceptExpression__ExpressionAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getAndObjectConceptExpressionAccess().getExpressionAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndObjectConceptExpression__Group__1__Impl"


    // $ANTLR start "rule__OrObjectConceptExpression__Group__0"
    // InternalArchcnl.g:2707:1: rule__OrObjectConceptExpression__Group__0 : rule__OrObjectConceptExpression__Group__0__Impl rule__OrObjectConceptExpression__Group__1 ;
    public final void rule__OrObjectConceptExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2711:1: ( rule__OrObjectConceptExpression__Group__0__Impl rule__OrObjectConceptExpression__Group__1 )
            // InternalArchcnl.g:2712:2: rule__OrObjectConceptExpression__Group__0__Impl rule__OrObjectConceptExpression__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__OrObjectConceptExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OrObjectConceptExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrObjectConceptExpression__Group__0"


    // $ANTLR start "rule__OrObjectConceptExpression__Group__0__Impl"
    // InternalArchcnl.g:2719:1: rule__OrObjectConceptExpression__Group__0__Impl : ( 'or' ) ;
    public final void rule__OrObjectConceptExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2723:1: ( ( 'or' ) )
            // InternalArchcnl.g:2724:1: ( 'or' )
            {
            // InternalArchcnl.g:2724:1: ( 'or' )
            // InternalArchcnl.g:2725:2: 'or'
            {
             before(grammarAccess.getOrObjectConceptExpressionAccess().getOrKeyword_0()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getOrObjectConceptExpressionAccess().getOrKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrObjectConceptExpression__Group__0__Impl"


    // $ANTLR start "rule__OrObjectConceptExpression__Group__1"
    // InternalArchcnl.g:2734:1: rule__OrObjectConceptExpression__Group__1 : rule__OrObjectConceptExpression__Group__1__Impl ;
    public final void rule__OrObjectConceptExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2738:1: ( rule__OrObjectConceptExpression__Group__1__Impl )
            // InternalArchcnl.g:2739:2: rule__OrObjectConceptExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OrObjectConceptExpression__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrObjectConceptExpression__Group__1"


    // $ANTLR start "rule__OrObjectConceptExpression__Group__1__Impl"
    // InternalArchcnl.g:2745:1: rule__OrObjectConceptExpression__Group__1__Impl : ( ( rule__OrObjectConceptExpression__ExpressionAssignment_1 ) ) ;
    public final void rule__OrObjectConceptExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2749:1: ( ( ( rule__OrObjectConceptExpression__ExpressionAssignment_1 ) ) )
            // InternalArchcnl.g:2750:1: ( ( rule__OrObjectConceptExpression__ExpressionAssignment_1 ) )
            {
            // InternalArchcnl.g:2750:1: ( ( rule__OrObjectConceptExpression__ExpressionAssignment_1 ) )
            // InternalArchcnl.g:2751:2: ( rule__OrObjectConceptExpression__ExpressionAssignment_1 )
            {
             before(grammarAccess.getOrObjectConceptExpressionAccess().getExpressionAssignment_1()); 
            // InternalArchcnl.g:2752:2: ( rule__OrObjectConceptExpression__ExpressionAssignment_1 )
            // InternalArchcnl.g:2752:3: rule__OrObjectConceptExpression__ExpressionAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__OrObjectConceptExpression__ExpressionAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getOrObjectConceptExpressionAccess().getExpressionAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrObjectConceptExpression__Group__1__Impl"


    // $ANTLR start "rule__DatatypeRelation__Group__0"
    // InternalArchcnl.g:2761:1: rule__DatatypeRelation__Group__0 : rule__DatatypeRelation__Group__0__Impl rule__DatatypeRelation__Group__1 ;
    public final void rule__DatatypeRelation__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2765:1: ( rule__DatatypeRelation__Group__0__Impl rule__DatatypeRelation__Group__1 )
            // InternalArchcnl.g:2766:2: rule__DatatypeRelation__Group__0__Impl rule__DatatypeRelation__Group__1
            {
            pushFollow(FOLLOW_21);
            rule__DatatypeRelation__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DatatypeRelation__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypeRelation__Group__0"


    // $ANTLR start "rule__DatatypeRelation__Group__0__Impl"
    // InternalArchcnl.g:2773:1: rule__DatatypeRelation__Group__0__Impl : ( ( rule__DatatypeRelation__RelationNameAssignment_0 ) ) ;
    public final void rule__DatatypeRelation__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2777:1: ( ( ( rule__DatatypeRelation__RelationNameAssignment_0 ) ) )
            // InternalArchcnl.g:2778:1: ( ( rule__DatatypeRelation__RelationNameAssignment_0 ) )
            {
            // InternalArchcnl.g:2778:1: ( ( rule__DatatypeRelation__RelationNameAssignment_0 ) )
            // InternalArchcnl.g:2779:2: ( rule__DatatypeRelation__RelationNameAssignment_0 )
            {
             before(grammarAccess.getDatatypeRelationAccess().getRelationNameAssignment_0()); 
            // InternalArchcnl.g:2780:2: ( rule__DatatypeRelation__RelationNameAssignment_0 )
            // InternalArchcnl.g:2780:3: rule__DatatypeRelation__RelationNameAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__DatatypeRelation__RelationNameAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getDatatypeRelationAccess().getRelationNameAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypeRelation__Group__0__Impl"


    // $ANTLR start "rule__DatatypeRelation__Group__1"
    // InternalArchcnl.g:2788:1: rule__DatatypeRelation__Group__1 : rule__DatatypeRelation__Group__1__Impl ;
    public final void rule__DatatypeRelation__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2792:1: ( rule__DatatypeRelation__Group__1__Impl )
            // InternalArchcnl.g:2793:2: rule__DatatypeRelation__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DatatypeRelation__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypeRelation__Group__1"


    // $ANTLR start "rule__DatatypeRelation__Group__1__Impl"
    // InternalArchcnl.g:2799:1: rule__DatatypeRelation__Group__1__Impl : ( 'equal-to' ) ;
    public final void rule__DatatypeRelation__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2803:1: ( ( 'equal-to' ) )
            // InternalArchcnl.g:2804:1: ( 'equal-to' )
            {
            // InternalArchcnl.g:2804:1: ( 'equal-to' )
            // InternalArchcnl.g:2805:2: 'equal-to'
            {
             before(grammarAccess.getDatatypeRelationAccess().getEqualToKeyword_1()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getDatatypeRelationAccess().getEqualToKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypeRelation__Group__1__Impl"


    // $ANTLR start "rule__ConceptExpression__Group__0"
    // InternalArchcnl.g:2815:1: rule__ConceptExpression__Group__0 : rule__ConceptExpression__Group__0__Impl rule__ConceptExpression__Group__1 ;
    public final void rule__ConceptExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2819:1: ( rule__ConceptExpression__Group__0__Impl rule__ConceptExpression__Group__1 )
            // InternalArchcnl.g:2820:2: rule__ConceptExpression__Group__0__Impl rule__ConceptExpression__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__ConceptExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConceptExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptExpression__Group__0"


    // $ANTLR start "rule__ConceptExpression__Group__0__Impl"
    // InternalArchcnl.g:2827:1: rule__ConceptExpression__Group__0__Impl : ( ( rule__ConceptExpression__Alternatives_0 )? ) ;
    public final void rule__ConceptExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2831:1: ( ( ( rule__ConceptExpression__Alternatives_0 )? ) )
            // InternalArchcnl.g:2832:1: ( ( rule__ConceptExpression__Alternatives_0 )? )
            {
            // InternalArchcnl.g:2832:1: ( ( rule__ConceptExpression__Alternatives_0 )? )
            // InternalArchcnl.g:2833:2: ( rule__ConceptExpression__Alternatives_0 )?
            {
             before(grammarAccess.getConceptExpressionAccess().getAlternatives_0()); 
            // InternalArchcnl.g:2834:2: ( rule__ConceptExpression__Alternatives_0 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( ((LA21_0>=17 && LA21_0<=18)) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalArchcnl.g:2834:3: rule__ConceptExpression__Alternatives_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ConceptExpression__Alternatives_0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getConceptExpressionAccess().getAlternatives_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptExpression__Group__0__Impl"


    // $ANTLR start "rule__ConceptExpression__Group__1"
    // InternalArchcnl.g:2842:1: rule__ConceptExpression__Group__1 : rule__ConceptExpression__Group__1__Impl rule__ConceptExpression__Group__2 ;
    public final void rule__ConceptExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2846:1: ( rule__ConceptExpression__Group__1__Impl rule__ConceptExpression__Group__2 )
            // InternalArchcnl.g:2847:2: rule__ConceptExpression__Group__1__Impl rule__ConceptExpression__Group__2
            {
            pushFollow(FOLLOW_22);
            rule__ConceptExpression__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConceptExpression__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptExpression__Group__1"


    // $ANTLR start "rule__ConceptExpression__Group__1__Impl"
    // InternalArchcnl.g:2854:1: rule__ConceptExpression__Group__1__Impl : ( ( rule__ConceptExpression__ConceptAssignment_1 ) ) ;
    public final void rule__ConceptExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2858:1: ( ( ( rule__ConceptExpression__ConceptAssignment_1 ) ) )
            // InternalArchcnl.g:2859:1: ( ( rule__ConceptExpression__ConceptAssignment_1 ) )
            {
            // InternalArchcnl.g:2859:1: ( ( rule__ConceptExpression__ConceptAssignment_1 ) )
            // InternalArchcnl.g:2860:2: ( rule__ConceptExpression__ConceptAssignment_1 )
            {
             before(grammarAccess.getConceptExpressionAccess().getConceptAssignment_1()); 
            // InternalArchcnl.g:2861:2: ( rule__ConceptExpression__ConceptAssignment_1 )
            // InternalArchcnl.g:2861:3: rule__ConceptExpression__ConceptAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__ConceptExpression__ConceptAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getConceptExpressionAccess().getConceptAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptExpression__Group__1__Impl"


    // $ANTLR start "rule__ConceptExpression__Group__2"
    // InternalArchcnl.g:2869:1: rule__ConceptExpression__Group__2 : rule__ConceptExpression__Group__2__Impl ;
    public final void rule__ConceptExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2873:1: ( rule__ConceptExpression__Group__2__Impl )
            // InternalArchcnl.g:2874:2: rule__ConceptExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ConceptExpression__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptExpression__Group__2"


    // $ANTLR start "rule__ConceptExpression__Group__2__Impl"
    // InternalArchcnl.g:2880:1: rule__ConceptExpression__Group__2__Impl : ( ( rule__ConceptExpression__ThatAssignment_2 )* ) ;
    public final void rule__ConceptExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2884:1: ( ( ( rule__ConceptExpression__ThatAssignment_2 )* ) )
            // InternalArchcnl.g:2885:1: ( ( rule__ConceptExpression__ThatAssignment_2 )* )
            {
            // InternalArchcnl.g:2885:1: ( ( rule__ConceptExpression__ThatAssignment_2 )* )
            // InternalArchcnl.g:2886:2: ( rule__ConceptExpression__ThatAssignment_2 )*
            {
             before(grammarAccess.getConceptExpressionAccess().getThatAssignment_2()); 
            // InternalArchcnl.g:2887:2: ( rule__ConceptExpression__ThatAssignment_2 )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==34) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalArchcnl.g:2887:3: rule__ConceptExpression__ThatAssignment_2
            	    {
            	    pushFollow(FOLLOW_23);
            	    rule__ConceptExpression__ThatAssignment_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

             after(grammarAccess.getConceptExpressionAccess().getThatAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptExpression__Group__2__Impl"


    // $ANTLR start "rule__ThatExpression__Group__0"
    // InternalArchcnl.g:2896:1: rule__ThatExpression__Group__0 : rule__ThatExpression__Group__0__Impl rule__ThatExpression__Group__1 ;
    public final void rule__ThatExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2900:1: ( rule__ThatExpression__Group__0__Impl rule__ThatExpression__Group__1 )
            // InternalArchcnl.g:2901:2: rule__ThatExpression__Group__0__Impl rule__ThatExpression__Group__1
            {
            pushFollow(FOLLOW_24);
            rule__ThatExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ThatExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ThatExpression__Group__0"


    // $ANTLR start "rule__ThatExpression__Group__0__Impl"
    // InternalArchcnl.g:2908:1: rule__ThatExpression__Group__0__Impl : ( 'that' ) ;
    public final void rule__ThatExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2912:1: ( ( 'that' ) )
            // InternalArchcnl.g:2913:1: ( 'that' )
            {
            // InternalArchcnl.g:2913:1: ( 'that' )
            // InternalArchcnl.g:2914:2: 'that'
            {
             before(grammarAccess.getThatExpressionAccess().getThatKeyword_0()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getThatExpressionAccess().getThatKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ThatExpression__Group__0__Impl"


    // $ANTLR start "rule__ThatExpression__Group__1"
    // InternalArchcnl.g:2923:1: rule__ThatExpression__Group__1 : rule__ThatExpression__Group__1__Impl rule__ThatExpression__Group__2 ;
    public final void rule__ThatExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2927:1: ( rule__ThatExpression__Group__1__Impl rule__ThatExpression__Group__2 )
            // InternalArchcnl.g:2928:2: rule__ThatExpression__Group__1__Impl rule__ThatExpression__Group__2
            {
            pushFollow(FOLLOW_25);
            rule__ThatExpression__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ThatExpression__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ThatExpression__Group__1"


    // $ANTLR start "rule__ThatExpression__Group__1__Impl"
    // InternalArchcnl.g:2935:1: rule__ThatExpression__Group__1__Impl : ( '(' ) ;
    public final void rule__ThatExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2939:1: ( ( '(' ) )
            // InternalArchcnl.g:2940:1: ( '(' )
            {
            // InternalArchcnl.g:2940:1: ( '(' )
            // InternalArchcnl.g:2941:2: '('
            {
             before(grammarAccess.getThatExpressionAccess().getLeftParenthesisKeyword_1()); 
            match(input,35,FOLLOW_2); 
             after(grammarAccess.getThatExpressionAccess().getLeftParenthesisKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ThatExpression__Group__1__Impl"


    // $ANTLR start "rule__ThatExpression__Group__2"
    // InternalArchcnl.g:2950:1: rule__ThatExpression__Group__2 : rule__ThatExpression__Group__2__Impl rule__ThatExpression__Group__3 ;
    public final void rule__ThatExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2954:1: ( rule__ThatExpression__Group__2__Impl rule__ThatExpression__Group__3 )
            // InternalArchcnl.g:2955:2: rule__ThatExpression__Group__2__Impl rule__ThatExpression__Group__3
            {
            pushFollow(FOLLOW_26);
            rule__ThatExpression__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ThatExpression__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ThatExpression__Group__2"


    // $ANTLR start "rule__ThatExpression__Group__2__Impl"
    // InternalArchcnl.g:2962:1: rule__ThatExpression__Group__2__Impl : ( ( ( rule__ThatExpression__ListAssignment_2 ) ) ( ( rule__ThatExpression__ListAssignment_2 )* ) ) ;
    public final void rule__ThatExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2966:1: ( ( ( ( rule__ThatExpression__ListAssignment_2 ) ) ( ( rule__ThatExpression__ListAssignment_2 )* ) ) )
            // InternalArchcnl.g:2967:1: ( ( ( rule__ThatExpression__ListAssignment_2 ) ) ( ( rule__ThatExpression__ListAssignment_2 )* ) )
            {
            // InternalArchcnl.g:2967:1: ( ( ( rule__ThatExpression__ListAssignment_2 ) ) ( ( rule__ThatExpression__ListAssignment_2 )* ) )
            // InternalArchcnl.g:2968:2: ( ( rule__ThatExpression__ListAssignment_2 ) ) ( ( rule__ThatExpression__ListAssignment_2 )* )
            {
            // InternalArchcnl.g:2968:2: ( ( rule__ThatExpression__ListAssignment_2 ) )
            // InternalArchcnl.g:2969:3: ( rule__ThatExpression__ListAssignment_2 )
            {
             before(grammarAccess.getThatExpressionAccess().getListAssignment_2()); 
            // InternalArchcnl.g:2970:3: ( rule__ThatExpression__ListAssignment_2 )
            // InternalArchcnl.g:2970:4: rule__ThatExpression__ListAssignment_2
            {
            pushFollow(FOLLOW_27);
            rule__ThatExpression__ListAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getThatExpressionAccess().getListAssignment_2()); 

            }

            // InternalArchcnl.g:2973:2: ( ( rule__ThatExpression__ListAssignment_2 )* )
            // InternalArchcnl.g:2974:3: ( rule__ThatExpression__ListAssignment_2 )*
            {
             before(grammarAccess.getThatExpressionAccess().getListAssignment_2()); 
            // InternalArchcnl.g:2975:3: ( rule__ThatExpression__ListAssignment_2 )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==RULE_RELATION_NAME||LA23_0==31) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalArchcnl.g:2975:4: rule__ThatExpression__ListAssignment_2
            	    {
            	    pushFollow(FOLLOW_27);
            	    rule__ThatExpression__ListAssignment_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

             after(grammarAccess.getThatExpressionAccess().getListAssignment_2()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ThatExpression__Group__2__Impl"


    // $ANTLR start "rule__ThatExpression__Group__3"
    // InternalArchcnl.g:2984:1: rule__ThatExpression__Group__3 : rule__ThatExpression__Group__3__Impl ;
    public final void rule__ThatExpression__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2988:1: ( rule__ThatExpression__Group__3__Impl )
            // InternalArchcnl.g:2989:2: rule__ThatExpression__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ThatExpression__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ThatExpression__Group__3"


    // $ANTLR start "rule__ThatExpression__Group__3__Impl"
    // InternalArchcnl.g:2995:1: rule__ThatExpression__Group__3__Impl : ( ')' ) ;
    public final void rule__ThatExpression__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:2999:1: ( ( ')' ) )
            // InternalArchcnl.g:3000:1: ( ')' )
            {
            // InternalArchcnl.g:3000:1: ( ')' )
            // InternalArchcnl.g:3001:2: ')'
            {
             before(grammarAccess.getThatExpressionAccess().getRightParenthesisKeyword_3()); 
            match(input,36,FOLLOW_2); 
             after(grammarAccess.getThatExpressionAccess().getRightParenthesisKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ThatExpression__Group__3__Impl"


    // $ANTLR start "rule__StatementList__Group__0"
    // InternalArchcnl.g:3011:1: rule__StatementList__Group__0 : rule__StatementList__Group__0__Impl rule__StatementList__Group__1 ;
    public final void rule__StatementList__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3015:1: ( rule__StatementList__Group__0__Impl rule__StatementList__Group__1 )
            // InternalArchcnl.g:3016:2: rule__StatementList__Group__0__Impl rule__StatementList__Group__1
            {
            pushFollow(FOLLOW_25);
            rule__StatementList__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StatementList__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__Group__0"


    // $ANTLR start "rule__StatementList__Group__0__Impl"
    // InternalArchcnl.g:3023:1: rule__StatementList__Group__0__Impl : ( ( 'and' )? ) ;
    public final void rule__StatementList__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3027:1: ( ( ( 'and' )? ) )
            // InternalArchcnl.g:3028:1: ( ( 'and' )? )
            {
            // InternalArchcnl.g:3028:1: ( ( 'and' )? )
            // InternalArchcnl.g:3029:2: ( 'and' )?
            {
             before(grammarAccess.getStatementListAccess().getAndKeyword_0()); 
            // InternalArchcnl.g:3030:2: ( 'and' )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==31) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalArchcnl.g:3030:3: 'and'
                    {
                    match(input,31,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getStatementListAccess().getAndKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__Group__0__Impl"


    // $ANTLR start "rule__StatementList__Group__1"
    // InternalArchcnl.g:3038:1: rule__StatementList__Group__1 : rule__StatementList__Group__1__Impl ;
    public final void rule__StatementList__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3042:1: ( rule__StatementList__Group__1__Impl )
            // InternalArchcnl.g:3043:2: rule__StatementList__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__StatementList__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__Group__1"


    // $ANTLR start "rule__StatementList__Group__1__Impl"
    // InternalArchcnl.g:3049:1: rule__StatementList__Group__1__Impl : ( ( rule__StatementList__Group_1__0 ) ) ;
    public final void rule__StatementList__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3053:1: ( ( ( rule__StatementList__Group_1__0 ) ) )
            // InternalArchcnl.g:3054:1: ( ( rule__StatementList__Group_1__0 ) )
            {
            // InternalArchcnl.g:3054:1: ( ( rule__StatementList__Group_1__0 ) )
            // InternalArchcnl.g:3055:2: ( rule__StatementList__Group_1__0 )
            {
             before(grammarAccess.getStatementListAccess().getGroup_1()); 
            // InternalArchcnl.g:3056:2: ( rule__StatementList__Group_1__0 )
            // InternalArchcnl.g:3056:3: rule__StatementList__Group_1__0
            {
            pushFollow(FOLLOW_2);
            rule__StatementList__Group_1__0();

            state._fsp--;


            }

             after(grammarAccess.getStatementListAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__Group__1__Impl"


    // $ANTLR start "rule__StatementList__Group_1__0"
    // InternalArchcnl.g:3065:1: rule__StatementList__Group_1__0 : rule__StatementList__Group_1__0__Impl rule__StatementList__Group_1__1 ;
    public final void rule__StatementList__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3069:1: ( rule__StatementList__Group_1__0__Impl rule__StatementList__Group_1__1 )
            // InternalArchcnl.g:3070:2: rule__StatementList__Group_1__0__Impl rule__StatementList__Group_1__1
            {
            pushFollow(FOLLOW_28);
            rule__StatementList__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StatementList__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__Group_1__0"


    // $ANTLR start "rule__StatementList__Group_1__0__Impl"
    // InternalArchcnl.g:3077:1: rule__StatementList__Group_1__0__Impl : ( ( rule__StatementList__RelationAssignment_1_0 ) ) ;
    public final void rule__StatementList__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3081:1: ( ( ( rule__StatementList__RelationAssignment_1_0 ) ) )
            // InternalArchcnl.g:3082:1: ( ( rule__StatementList__RelationAssignment_1_0 ) )
            {
            // InternalArchcnl.g:3082:1: ( ( rule__StatementList__RelationAssignment_1_0 ) )
            // InternalArchcnl.g:3083:2: ( rule__StatementList__RelationAssignment_1_0 )
            {
             before(grammarAccess.getStatementListAccess().getRelationAssignment_1_0()); 
            // InternalArchcnl.g:3084:2: ( rule__StatementList__RelationAssignment_1_0 )
            // InternalArchcnl.g:3084:3: rule__StatementList__RelationAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__StatementList__RelationAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getStatementListAccess().getRelationAssignment_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__Group_1__0__Impl"


    // $ANTLR start "rule__StatementList__Group_1__1"
    // InternalArchcnl.g:3092:1: rule__StatementList__Group_1__1 : rule__StatementList__Group_1__1__Impl ;
    public final void rule__StatementList__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3096:1: ( rule__StatementList__Group_1__1__Impl )
            // InternalArchcnl.g:3097:2: rule__StatementList__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__StatementList__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__Group_1__1"


    // $ANTLR start "rule__StatementList__Group_1__1__Impl"
    // InternalArchcnl.g:3103:1: rule__StatementList__Group_1__1__Impl : ( ( rule__StatementList__Alternatives_1_1 ) ) ;
    public final void rule__StatementList__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3107:1: ( ( ( rule__StatementList__Alternatives_1_1 ) ) )
            // InternalArchcnl.g:3108:1: ( ( rule__StatementList__Alternatives_1_1 ) )
            {
            // InternalArchcnl.g:3108:1: ( ( rule__StatementList__Alternatives_1_1 ) )
            // InternalArchcnl.g:3109:2: ( rule__StatementList__Alternatives_1_1 )
            {
             before(grammarAccess.getStatementListAccess().getAlternatives_1_1()); 
            // InternalArchcnl.g:3110:2: ( rule__StatementList__Alternatives_1_1 )
            // InternalArchcnl.g:3110:3: rule__StatementList__Alternatives_1_1
            {
            pushFollow(FOLLOW_2);
            rule__StatementList__Alternatives_1_1();

            state._fsp--;


            }

             after(grammarAccess.getStatementListAccess().getAlternatives_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__Group_1__1__Impl"


    // $ANTLR start "rule__VariableStatement__Group__0"
    // InternalArchcnl.g:3119:1: rule__VariableStatement__Group__0 : rule__VariableStatement__Group__0__Impl rule__VariableStatement__Group__1 ;
    public final void rule__VariableStatement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3123:1: ( rule__VariableStatement__Group__0__Impl rule__VariableStatement__Group__1 )
            // InternalArchcnl.g:3124:2: rule__VariableStatement__Group__0__Impl rule__VariableStatement__Group__1
            {
            pushFollow(FOLLOW_29);
            rule__VariableStatement__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VariableStatement__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableStatement__Group__0"


    // $ANTLR start "rule__VariableStatement__Group__0__Impl"
    // InternalArchcnl.g:3131:1: rule__VariableStatement__Group__0__Impl : ( ( rule__VariableStatement__ConceptAssignment_0 ) ) ;
    public final void rule__VariableStatement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3135:1: ( ( ( rule__VariableStatement__ConceptAssignment_0 ) ) )
            // InternalArchcnl.g:3136:1: ( ( rule__VariableStatement__ConceptAssignment_0 ) )
            {
            // InternalArchcnl.g:3136:1: ( ( rule__VariableStatement__ConceptAssignment_0 ) )
            // InternalArchcnl.g:3137:2: ( rule__VariableStatement__ConceptAssignment_0 )
            {
             before(grammarAccess.getVariableStatementAccess().getConceptAssignment_0()); 
            // InternalArchcnl.g:3138:2: ( rule__VariableStatement__ConceptAssignment_0 )
            // InternalArchcnl.g:3138:3: rule__VariableStatement__ConceptAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__VariableStatement__ConceptAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getVariableStatementAccess().getConceptAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableStatement__Group__0__Impl"


    // $ANTLR start "rule__VariableStatement__Group__1"
    // InternalArchcnl.g:3146:1: rule__VariableStatement__Group__1 : rule__VariableStatement__Group__1__Impl ;
    public final void rule__VariableStatement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3150:1: ( rule__VariableStatement__Group__1__Impl )
            // InternalArchcnl.g:3151:2: rule__VariableStatement__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VariableStatement__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableStatement__Group__1"


    // $ANTLR start "rule__VariableStatement__Group__1__Impl"
    // InternalArchcnl.g:3157:1: rule__VariableStatement__Group__1__Impl : ( ( rule__VariableStatement__VariableAssignment_1 ) ) ;
    public final void rule__VariableStatement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3161:1: ( ( ( rule__VariableStatement__VariableAssignment_1 ) ) )
            // InternalArchcnl.g:3162:1: ( ( rule__VariableStatement__VariableAssignment_1 ) )
            {
            // InternalArchcnl.g:3162:1: ( ( rule__VariableStatement__VariableAssignment_1 ) )
            // InternalArchcnl.g:3163:2: ( rule__VariableStatement__VariableAssignment_1 )
            {
             before(grammarAccess.getVariableStatementAccess().getVariableAssignment_1()); 
            // InternalArchcnl.g:3164:2: ( rule__VariableStatement__VariableAssignment_1 )
            // InternalArchcnl.g:3164:3: rule__VariableStatement__VariableAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__VariableStatement__VariableAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getVariableStatementAccess().getVariableAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableStatement__Group__1__Impl"


    // $ANTLR start "rule__FactStatement__Group__0"
    // InternalArchcnl.g:3173:1: rule__FactStatement__Group__0 : rule__FactStatement__Group__0__Impl rule__FactStatement__Group__1 ;
    public final void rule__FactStatement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3177:1: ( rule__FactStatement__Group__0__Impl rule__FactStatement__Group__1 )
            // InternalArchcnl.g:3178:2: rule__FactStatement__Group__0__Impl rule__FactStatement__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__FactStatement__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FactStatement__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FactStatement__Group__0"


    // $ANTLR start "rule__FactStatement__Group__0__Impl"
    // InternalArchcnl.g:3185:1: rule__FactStatement__Group__0__Impl : ( ( rule__FactStatement__Alternatives_0 ) ) ;
    public final void rule__FactStatement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3189:1: ( ( ( rule__FactStatement__Alternatives_0 ) ) )
            // InternalArchcnl.g:3190:1: ( ( rule__FactStatement__Alternatives_0 ) )
            {
            // InternalArchcnl.g:3190:1: ( ( rule__FactStatement__Alternatives_0 ) )
            // InternalArchcnl.g:3191:2: ( rule__FactStatement__Alternatives_0 )
            {
             before(grammarAccess.getFactStatementAccess().getAlternatives_0()); 
            // InternalArchcnl.g:3192:2: ( rule__FactStatement__Alternatives_0 )
            // InternalArchcnl.g:3192:3: rule__FactStatement__Alternatives_0
            {
            pushFollow(FOLLOW_2);
            rule__FactStatement__Alternatives_0();

            state._fsp--;


            }

             after(grammarAccess.getFactStatementAccess().getAlternatives_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FactStatement__Group__0__Impl"


    // $ANTLR start "rule__FactStatement__Group__1"
    // InternalArchcnl.g:3200:1: rule__FactStatement__Group__1 : rule__FactStatement__Group__1__Impl ;
    public final void rule__FactStatement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3204:1: ( rule__FactStatement__Group__1__Impl )
            // InternalArchcnl.g:3205:2: rule__FactStatement__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__FactStatement__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FactStatement__Group__1"


    // $ANTLR start "rule__FactStatement__Group__1__Impl"
    // InternalArchcnl.g:3211:1: rule__FactStatement__Group__1__Impl : ( '.' ) ;
    public final void rule__FactStatement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3215:1: ( ( '.' ) )
            // InternalArchcnl.g:3216:1: ( '.' )
            {
            // InternalArchcnl.g:3216:1: ( '.' )
            // InternalArchcnl.g:3217:2: '.'
            {
             before(grammarAccess.getFactStatementAccess().getFullStopKeyword_1()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getFactStatementAccess().getFullStopKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FactStatement__Group__1__Impl"


    // $ANTLR start "rule__ConceptAssertion__Group__0"
    // InternalArchcnl.g:3227:1: rule__ConceptAssertion__Group__0 : rule__ConceptAssertion__Group__0__Impl rule__ConceptAssertion__Group__1 ;
    public final void rule__ConceptAssertion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3231:1: ( rule__ConceptAssertion__Group__0__Impl rule__ConceptAssertion__Group__1 )
            // InternalArchcnl.g:3232:2: rule__ConceptAssertion__Group__0__Impl rule__ConceptAssertion__Group__1
            {
            pushFollow(FOLLOW_30);
            rule__ConceptAssertion__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConceptAssertion__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__Group__0"


    // $ANTLR start "rule__ConceptAssertion__Group__0__Impl"
    // InternalArchcnl.g:3239:1: rule__ConceptAssertion__Group__0__Impl : ( ( rule__ConceptAssertion__IndividualAssignment_0 ) ) ;
    public final void rule__ConceptAssertion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3243:1: ( ( ( rule__ConceptAssertion__IndividualAssignment_0 ) ) )
            // InternalArchcnl.g:3244:1: ( ( rule__ConceptAssertion__IndividualAssignment_0 ) )
            {
            // InternalArchcnl.g:3244:1: ( ( rule__ConceptAssertion__IndividualAssignment_0 ) )
            // InternalArchcnl.g:3245:2: ( rule__ConceptAssertion__IndividualAssignment_0 )
            {
             before(grammarAccess.getConceptAssertionAccess().getIndividualAssignment_0()); 
            // InternalArchcnl.g:3246:2: ( rule__ConceptAssertion__IndividualAssignment_0 )
            // InternalArchcnl.g:3246:3: rule__ConceptAssertion__IndividualAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__ConceptAssertion__IndividualAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getConceptAssertionAccess().getIndividualAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__Group__0__Impl"


    // $ANTLR start "rule__ConceptAssertion__Group__1"
    // InternalArchcnl.g:3254:1: rule__ConceptAssertion__Group__1 : rule__ConceptAssertion__Group__1__Impl rule__ConceptAssertion__Group__2 ;
    public final void rule__ConceptAssertion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3258:1: ( rule__ConceptAssertion__Group__1__Impl rule__ConceptAssertion__Group__2 )
            // InternalArchcnl.g:3259:2: rule__ConceptAssertion__Group__1__Impl rule__ConceptAssertion__Group__2
            {
            pushFollow(FOLLOW_31);
            rule__ConceptAssertion__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConceptAssertion__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__Group__1"


    // $ANTLR start "rule__ConceptAssertion__Group__1__Impl"
    // InternalArchcnl.g:3266:1: rule__ConceptAssertion__Group__1__Impl : ( 'is' ) ;
    public final void rule__ConceptAssertion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3270:1: ( ( 'is' ) )
            // InternalArchcnl.g:3271:1: ( 'is' )
            {
            // InternalArchcnl.g:3271:1: ( 'is' )
            // InternalArchcnl.g:3272:2: 'is'
            {
             before(grammarAccess.getConceptAssertionAccess().getIsKeyword_1()); 
            match(input,37,FOLLOW_2); 
             after(grammarAccess.getConceptAssertionAccess().getIsKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__Group__1__Impl"


    // $ANTLR start "rule__ConceptAssertion__Group__2"
    // InternalArchcnl.g:3281:1: rule__ConceptAssertion__Group__2 : rule__ConceptAssertion__Group__2__Impl rule__ConceptAssertion__Group__3 ;
    public final void rule__ConceptAssertion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3285:1: ( rule__ConceptAssertion__Group__2__Impl rule__ConceptAssertion__Group__3 )
            // InternalArchcnl.g:3286:2: rule__ConceptAssertion__Group__2__Impl rule__ConceptAssertion__Group__3
            {
            pushFollow(FOLLOW_4);
            rule__ConceptAssertion__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConceptAssertion__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__Group__2"


    // $ANTLR start "rule__ConceptAssertion__Group__2__Impl"
    // InternalArchcnl.g:3293:1: rule__ConceptAssertion__Group__2__Impl : ( ( rule__ConceptAssertion__Alternatives_2 ) ) ;
    public final void rule__ConceptAssertion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3297:1: ( ( ( rule__ConceptAssertion__Alternatives_2 ) ) )
            // InternalArchcnl.g:3298:1: ( ( rule__ConceptAssertion__Alternatives_2 ) )
            {
            // InternalArchcnl.g:3298:1: ( ( rule__ConceptAssertion__Alternatives_2 ) )
            // InternalArchcnl.g:3299:2: ( rule__ConceptAssertion__Alternatives_2 )
            {
             before(grammarAccess.getConceptAssertionAccess().getAlternatives_2()); 
            // InternalArchcnl.g:3300:2: ( rule__ConceptAssertion__Alternatives_2 )
            // InternalArchcnl.g:3300:3: rule__ConceptAssertion__Alternatives_2
            {
            pushFollow(FOLLOW_2);
            rule__ConceptAssertion__Alternatives_2();

            state._fsp--;


            }

             after(grammarAccess.getConceptAssertionAccess().getAlternatives_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__Group__2__Impl"


    // $ANTLR start "rule__ConceptAssertion__Group__3"
    // InternalArchcnl.g:3308:1: rule__ConceptAssertion__Group__3 : rule__ConceptAssertion__Group__3__Impl rule__ConceptAssertion__Group__4 ;
    public final void rule__ConceptAssertion__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3312:1: ( rule__ConceptAssertion__Group__3__Impl rule__ConceptAssertion__Group__4 )
            // InternalArchcnl.g:3313:2: rule__ConceptAssertion__Group__3__Impl rule__ConceptAssertion__Group__4
            {
            pushFollow(FOLLOW_6);
            rule__ConceptAssertion__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ConceptAssertion__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__Group__3"


    // $ANTLR start "rule__ConceptAssertion__Group__3__Impl"
    // InternalArchcnl.g:3320:1: rule__ConceptAssertion__Group__3__Impl : ( ( rule__ConceptAssertion__ConceptAssignment_3 ) ) ;
    public final void rule__ConceptAssertion__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3324:1: ( ( ( rule__ConceptAssertion__ConceptAssignment_3 ) ) )
            // InternalArchcnl.g:3325:1: ( ( rule__ConceptAssertion__ConceptAssignment_3 ) )
            {
            // InternalArchcnl.g:3325:1: ( ( rule__ConceptAssertion__ConceptAssignment_3 ) )
            // InternalArchcnl.g:3326:2: ( rule__ConceptAssertion__ConceptAssignment_3 )
            {
             before(grammarAccess.getConceptAssertionAccess().getConceptAssignment_3()); 
            // InternalArchcnl.g:3327:2: ( rule__ConceptAssertion__ConceptAssignment_3 )
            // InternalArchcnl.g:3327:3: rule__ConceptAssertion__ConceptAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__ConceptAssertion__ConceptAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getConceptAssertionAccess().getConceptAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__Group__3__Impl"


    // $ANTLR start "rule__ConceptAssertion__Group__4"
    // InternalArchcnl.g:3335:1: rule__ConceptAssertion__Group__4 : rule__ConceptAssertion__Group__4__Impl ;
    public final void rule__ConceptAssertion__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3339:1: ( rule__ConceptAssertion__Group__4__Impl )
            // InternalArchcnl.g:3340:2: rule__ConceptAssertion__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ConceptAssertion__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__Group__4"


    // $ANTLR start "rule__ConceptAssertion__Group__4__Impl"
    // InternalArchcnl.g:3346:1: rule__ConceptAssertion__Group__4__Impl : ( '.' ) ;
    public final void rule__ConceptAssertion__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3350:1: ( ( '.' ) )
            // InternalArchcnl.g:3351:1: ( '.' )
            {
            // InternalArchcnl.g:3351:1: ( '.' )
            // InternalArchcnl.g:3352:2: '.'
            {
             before(grammarAccess.getConceptAssertionAccess().getFullStopKeyword_4()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getConceptAssertionAccess().getFullStopKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__Group__4__Impl"


    // $ANTLR start "rule__RoleAssertion__Group_1__0"
    // InternalArchcnl.g:3362:1: rule__RoleAssertion__Group_1__0 : rule__RoleAssertion__Group_1__0__Impl rule__RoleAssertion__Group_1__1 ;
    public final void rule__RoleAssertion__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3366:1: ( rule__RoleAssertion__Group_1__0__Impl rule__RoleAssertion__Group_1__1 )
            // InternalArchcnl.g:3367:2: rule__RoleAssertion__Group_1__0__Impl rule__RoleAssertion__Group_1__1
            {
            pushFollow(FOLLOW_6);
            rule__RoleAssertion__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RoleAssertion__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RoleAssertion__Group_1__0"


    // $ANTLR start "rule__RoleAssertion__Group_1__0__Impl"
    // InternalArchcnl.g:3374:1: rule__RoleAssertion__Group_1__0__Impl : ( ruleDatatypePropertyAssertion ) ;
    public final void rule__RoleAssertion__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3378:1: ( ( ruleDatatypePropertyAssertion ) )
            // InternalArchcnl.g:3379:1: ( ruleDatatypePropertyAssertion )
            {
            // InternalArchcnl.g:3379:1: ( ruleDatatypePropertyAssertion )
            // InternalArchcnl.g:3380:2: ruleDatatypePropertyAssertion
            {
             before(grammarAccess.getRoleAssertionAccess().getDatatypePropertyAssertionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDatatypePropertyAssertion();

            state._fsp--;

             after(grammarAccess.getRoleAssertionAccess().getDatatypePropertyAssertionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RoleAssertion__Group_1__0__Impl"


    // $ANTLR start "rule__RoleAssertion__Group_1__1"
    // InternalArchcnl.g:3389:1: rule__RoleAssertion__Group_1__1 : rule__RoleAssertion__Group_1__1__Impl ;
    public final void rule__RoleAssertion__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3393:1: ( rule__RoleAssertion__Group_1__1__Impl )
            // InternalArchcnl.g:3394:2: rule__RoleAssertion__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RoleAssertion__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RoleAssertion__Group_1__1"


    // $ANTLR start "rule__RoleAssertion__Group_1__1__Impl"
    // InternalArchcnl.g:3400:1: rule__RoleAssertion__Group_1__1__Impl : ( '.' ) ;
    public final void rule__RoleAssertion__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3404:1: ( ( '.' ) )
            // InternalArchcnl.g:3405:1: ( '.' )
            {
            // InternalArchcnl.g:3405:1: ( '.' )
            // InternalArchcnl.g:3406:2: '.'
            {
             before(grammarAccess.getRoleAssertionAccess().getFullStopKeyword_1_1()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getRoleAssertionAccess().getFullStopKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RoleAssertion__Group_1__1__Impl"


    // $ANTLR start "rule__DatatypePropertyAssertion__Group__0"
    // InternalArchcnl.g:3416:1: rule__DatatypePropertyAssertion__Group__0 : rule__DatatypePropertyAssertion__Group__0__Impl rule__DatatypePropertyAssertion__Group__1 ;
    public final void rule__DatatypePropertyAssertion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3420:1: ( rule__DatatypePropertyAssertion__Group__0__Impl rule__DatatypePropertyAssertion__Group__1 )
            // InternalArchcnl.g:3421:2: rule__DatatypePropertyAssertion__Group__0__Impl rule__DatatypePropertyAssertion__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__DatatypePropertyAssertion__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DatatypePropertyAssertion__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypePropertyAssertion__Group__0"


    // $ANTLR start "rule__DatatypePropertyAssertion__Group__0__Impl"
    // InternalArchcnl.g:3428:1: rule__DatatypePropertyAssertion__Group__0__Impl : ( ( rule__DatatypePropertyAssertion__IndividualAssignment_0 ) ) ;
    public final void rule__DatatypePropertyAssertion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3432:1: ( ( ( rule__DatatypePropertyAssertion__IndividualAssignment_0 ) ) )
            // InternalArchcnl.g:3433:1: ( ( rule__DatatypePropertyAssertion__IndividualAssignment_0 ) )
            {
            // InternalArchcnl.g:3433:1: ( ( rule__DatatypePropertyAssertion__IndividualAssignment_0 ) )
            // InternalArchcnl.g:3434:2: ( rule__DatatypePropertyAssertion__IndividualAssignment_0 )
            {
             before(grammarAccess.getDatatypePropertyAssertionAccess().getIndividualAssignment_0()); 
            // InternalArchcnl.g:3435:2: ( rule__DatatypePropertyAssertion__IndividualAssignment_0 )
            // InternalArchcnl.g:3435:3: rule__DatatypePropertyAssertion__IndividualAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__DatatypePropertyAssertion__IndividualAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getDatatypePropertyAssertionAccess().getIndividualAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypePropertyAssertion__Group__0__Impl"


    // $ANTLR start "rule__DatatypePropertyAssertion__Group__1"
    // InternalArchcnl.g:3443:1: rule__DatatypePropertyAssertion__Group__1 : rule__DatatypePropertyAssertion__Group__1__Impl rule__DatatypePropertyAssertion__Group__2 ;
    public final void rule__DatatypePropertyAssertion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3447:1: ( rule__DatatypePropertyAssertion__Group__1__Impl rule__DatatypePropertyAssertion__Group__2 )
            // InternalArchcnl.g:3448:2: rule__DatatypePropertyAssertion__Group__1__Impl rule__DatatypePropertyAssertion__Group__2
            {
            pushFollow(FOLLOW_20);
            rule__DatatypePropertyAssertion__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DatatypePropertyAssertion__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypePropertyAssertion__Group__1"


    // $ANTLR start "rule__DatatypePropertyAssertion__Group__1__Impl"
    // InternalArchcnl.g:3455:1: rule__DatatypePropertyAssertion__Group__1__Impl : ( ( rule__DatatypePropertyAssertion__RelationAssignment_1 ) ) ;
    public final void rule__DatatypePropertyAssertion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3459:1: ( ( ( rule__DatatypePropertyAssertion__RelationAssignment_1 ) ) )
            // InternalArchcnl.g:3460:1: ( ( rule__DatatypePropertyAssertion__RelationAssignment_1 ) )
            {
            // InternalArchcnl.g:3460:1: ( ( rule__DatatypePropertyAssertion__RelationAssignment_1 ) )
            // InternalArchcnl.g:3461:2: ( rule__DatatypePropertyAssertion__RelationAssignment_1 )
            {
             before(grammarAccess.getDatatypePropertyAssertionAccess().getRelationAssignment_1()); 
            // InternalArchcnl.g:3462:2: ( rule__DatatypePropertyAssertion__RelationAssignment_1 )
            // InternalArchcnl.g:3462:3: rule__DatatypePropertyAssertion__RelationAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__DatatypePropertyAssertion__RelationAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getDatatypePropertyAssertionAccess().getRelationAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypePropertyAssertion__Group__1__Impl"


    // $ANTLR start "rule__DatatypePropertyAssertion__Group__2"
    // InternalArchcnl.g:3470:1: rule__DatatypePropertyAssertion__Group__2 : rule__DatatypePropertyAssertion__Group__2__Impl ;
    public final void rule__DatatypePropertyAssertion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3474:1: ( rule__DatatypePropertyAssertion__Group__2__Impl )
            // InternalArchcnl.g:3475:2: rule__DatatypePropertyAssertion__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DatatypePropertyAssertion__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypePropertyAssertion__Group__2"


    // $ANTLR start "rule__DatatypePropertyAssertion__Group__2__Impl"
    // InternalArchcnl.g:3481:1: rule__DatatypePropertyAssertion__Group__2__Impl : ( ( rule__DatatypePropertyAssertion__Alternatives_2 ) ) ;
    public final void rule__DatatypePropertyAssertion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3485:1: ( ( ( rule__DatatypePropertyAssertion__Alternatives_2 ) ) )
            // InternalArchcnl.g:3486:1: ( ( rule__DatatypePropertyAssertion__Alternatives_2 ) )
            {
            // InternalArchcnl.g:3486:1: ( ( rule__DatatypePropertyAssertion__Alternatives_2 ) )
            // InternalArchcnl.g:3487:2: ( rule__DatatypePropertyAssertion__Alternatives_2 )
            {
             before(grammarAccess.getDatatypePropertyAssertionAccess().getAlternatives_2()); 
            // InternalArchcnl.g:3488:2: ( rule__DatatypePropertyAssertion__Alternatives_2 )
            // InternalArchcnl.g:3488:3: rule__DatatypePropertyAssertion__Alternatives_2
            {
            pushFollow(FOLLOW_2);
            rule__DatatypePropertyAssertion__Alternatives_2();

            state._fsp--;


            }

             after(grammarAccess.getDatatypePropertyAssertionAccess().getAlternatives_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypePropertyAssertion__Group__2__Impl"


    // $ANTLR start "rule__ObjectPropertyAssertion__Group__0"
    // InternalArchcnl.g:3497:1: rule__ObjectPropertyAssertion__Group__0 : rule__ObjectPropertyAssertion__Group__0__Impl rule__ObjectPropertyAssertion__Group__1 ;
    public final void rule__ObjectPropertyAssertion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3501:1: ( rule__ObjectPropertyAssertion__Group__0__Impl rule__ObjectPropertyAssertion__Group__1 )
            // InternalArchcnl.g:3502:2: rule__ObjectPropertyAssertion__Group__0__Impl rule__ObjectPropertyAssertion__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__ObjectPropertyAssertion__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ObjectPropertyAssertion__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__Group__0"


    // $ANTLR start "rule__ObjectPropertyAssertion__Group__0__Impl"
    // InternalArchcnl.g:3509:1: rule__ObjectPropertyAssertion__Group__0__Impl : ( ( rule__ObjectPropertyAssertion__IndividualAssignment_0 ) ) ;
    public final void rule__ObjectPropertyAssertion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3513:1: ( ( ( rule__ObjectPropertyAssertion__IndividualAssignment_0 ) ) )
            // InternalArchcnl.g:3514:1: ( ( rule__ObjectPropertyAssertion__IndividualAssignment_0 ) )
            {
            // InternalArchcnl.g:3514:1: ( ( rule__ObjectPropertyAssertion__IndividualAssignment_0 ) )
            // InternalArchcnl.g:3515:2: ( rule__ObjectPropertyAssertion__IndividualAssignment_0 )
            {
             before(grammarAccess.getObjectPropertyAssertionAccess().getIndividualAssignment_0()); 
            // InternalArchcnl.g:3516:2: ( rule__ObjectPropertyAssertion__IndividualAssignment_0 )
            // InternalArchcnl.g:3516:3: rule__ObjectPropertyAssertion__IndividualAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__ObjectPropertyAssertion__IndividualAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getObjectPropertyAssertionAccess().getIndividualAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__Group__0__Impl"


    // $ANTLR start "rule__ObjectPropertyAssertion__Group__1"
    // InternalArchcnl.g:3524:1: rule__ObjectPropertyAssertion__Group__1 : rule__ObjectPropertyAssertion__Group__1__Impl rule__ObjectPropertyAssertion__Group__2 ;
    public final void rule__ObjectPropertyAssertion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3528:1: ( rule__ObjectPropertyAssertion__Group__1__Impl rule__ObjectPropertyAssertion__Group__2 )
            // InternalArchcnl.g:3529:2: rule__ObjectPropertyAssertion__Group__1__Impl rule__ObjectPropertyAssertion__Group__2
            {
            pushFollow(FOLLOW_31);
            rule__ObjectPropertyAssertion__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ObjectPropertyAssertion__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__Group__1"


    // $ANTLR start "rule__ObjectPropertyAssertion__Group__1__Impl"
    // InternalArchcnl.g:3536:1: rule__ObjectPropertyAssertion__Group__1__Impl : ( ( rule__ObjectPropertyAssertion__RelationAssignment_1 ) ) ;
    public final void rule__ObjectPropertyAssertion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3540:1: ( ( ( rule__ObjectPropertyAssertion__RelationAssignment_1 ) ) )
            // InternalArchcnl.g:3541:1: ( ( rule__ObjectPropertyAssertion__RelationAssignment_1 ) )
            {
            // InternalArchcnl.g:3541:1: ( ( rule__ObjectPropertyAssertion__RelationAssignment_1 ) )
            // InternalArchcnl.g:3542:2: ( rule__ObjectPropertyAssertion__RelationAssignment_1 )
            {
             before(grammarAccess.getObjectPropertyAssertionAccess().getRelationAssignment_1()); 
            // InternalArchcnl.g:3543:2: ( rule__ObjectPropertyAssertion__RelationAssignment_1 )
            // InternalArchcnl.g:3543:3: rule__ObjectPropertyAssertion__RelationAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__ObjectPropertyAssertion__RelationAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getObjectPropertyAssertionAccess().getRelationAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__Group__1__Impl"


    // $ANTLR start "rule__ObjectPropertyAssertion__Group__2"
    // InternalArchcnl.g:3551:1: rule__ObjectPropertyAssertion__Group__2 : rule__ObjectPropertyAssertion__Group__2__Impl rule__ObjectPropertyAssertion__Group__3 ;
    public final void rule__ObjectPropertyAssertion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3555:1: ( rule__ObjectPropertyAssertion__Group__2__Impl rule__ObjectPropertyAssertion__Group__3 )
            // InternalArchcnl.g:3556:2: rule__ObjectPropertyAssertion__Group__2__Impl rule__ObjectPropertyAssertion__Group__3
            {
            pushFollow(FOLLOW_4);
            rule__ObjectPropertyAssertion__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ObjectPropertyAssertion__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__Group__2"


    // $ANTLR start "rule__ObjectPropertyAssertion__Group__2__Impl"
    // InternalArchcnl.g:3563:1: rule__ObjectPropertyAssertion__Group__2__Impl : ( ( rule__ObjectPropertyAssertion__Alternatives_2 ) ) ;
    public final void rule__ObjectPropertyAssertion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3567:1: ( ( ( rule__ObjectPropertyAssertion__Alternatives_2 ) ) )
            // InternalArchcnl.g:3568:1: ( ( rule__ObjectPropertyAssertion__Alternatives_2 ) )
            {
            // InternalArchcnl.g:3568:1: ( ( rule__ObjectPropertyAssertion__Alternatives_2 ) )
            // InternalArchcnl.g:3569:2: ( rule__ObjectPropertyAssertion__Alternatives_2 )
            {
             before(grammarAccess.getObjectPropertyAssertionAccess().getAlternatives_2()); 
            // InternalArchcnl.g:3570:2: ( rule__ObjectPropertyAssertion__Alternatives_2 )
            // InternalArchcnl.g:3570:3: rule__ObjectPropertyAssertion__Alternatives_2
            {
            pushFollow(FOLLOW_2);
            rule__ObjectPropertyAssertion__Alternatives_2();

            state._fsp--;


            }

             after(grammarAccess.getObjectPropertyAssertionAccess().getAlternatives_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__Group__2__Impl"


    // $ANTLR start "rule__ObjectPropertyAssertion__Group__3"
    // InternalArchcnl.g:3578:1: rule__ObjectPropertyAssertion__Group__3 : rule__ObjectPropertyAssertion__Group__3__Impl ;
    public final void rule__ObjectPropertyAssertion__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3582:1: ( rule__ObjectPropertyAssertion__Group__3__Impl )
            // InternalArchcnl.g:3583:2: rule__ObjectPropertyAssertion__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ObjectPropertyAssertion__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__Group__3"


    // $ANTLR start "rule__ObjectPropertyAssertion__Group__3__Impl"
    // InternalArchcnl.g:3589:1: rule__ObjectPropertyAssertion__Group__3__Impl : ( ( rule__ObjectPropertyAssertion__ConceptAssignment_3 ) ) ;
    public final void rule__ObjectPropertyAssertion__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3593:1: ( ( ( rule__ObjectPropertyAssertion__ConceptAssignment_3 ) ) )
            // InternalArchcnl.g:3594:1: ( ( rule__ObjectPropertyAssertion__ConceptAssignment_3 ) )
            {
            // InternalArchcnl.g:3594:1: ( ( rule__ObjectPropertyAssertion__ConceptAssignment_3 ) )
            // InternalArchcnl.g:3595:2: ( rule__ObjectPropertyAssertion__ConceptAssignment_3 )
            {
             before(grammarAccess.getObjectPropertyAssertionAccess().getConceptAssignment_3()); 
            // InternalArchcnl.g:3596:2: ( rule__ObjectPropertyAssertion__ConceptAssignment_3 )
            // InternalArchcnl.g:3596:3: rule__ObjectPropertyAssertion__ConceptAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__ObjectPropertyAssertion__ConceptAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getObjectPropertyAssertionAccess().getConceptAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__Group__3__Impl"


    // $ANTLR start "rule__Model__SentenceAssignment"
    // InternalArchcnl.g:3605:1: rule__Model__SentenceAssignment : ( ruleSentence ) ;
    public final void rule__Model__SentenceAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3609:1: ( ( ruleSentence ) )
            // InternalArchcnl.g:3610:2: ( ruleSentence )
            {
            // InternalArchcnl.g:3610:2: ( ruleSentence )
            // InternalArchcnl.g:3611:3: ruleSentence
            {
             before(grammarAccess.getModelAccess().getSentenceSentenceParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleSentence();

            state._fsp--;

             after(grammarAccess.getModelAccess().getSentenceSentenceParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__SentenceAssignment"


    // $ANTLR start "rule__Sentence__SubjectAssignment_0_1"
    // InternalArchcnl.g:3620:1: rule__Sentence__SubjectAssignment_0_1 : ( ruleConceptExpression ) ;
    public final void rule__Sentence__SubjectAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3624:1: ( ( ruleConceptExpression ) )
            // InternalArchcnl.g:3625:2: ( ruleConceptExpression )
            {
            // InternalArchcnl.g:3625:2: ( ruleConceptExpression )
            // InternalArchcnl.g:3626:3: ruleConceptExpression
            {
             before(grammarAccess.getSentenceAccess().getSubjectConceptExpressionParserRuleCall_0_1_0()); 
            pushFollow(FOLLOW_2);
            ruleConceptExpression();

            state._fsp--;

             after(grammarAccess.getSentenceAccess().getSubjectConceptExpressionParserRuleCall_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__SubjectAssignment_0_1"


    // $ANTLR start "rule__Sentence__RuletypeAssignment_0_2"
    // InternalArchcnl.g:3635:1: rule__Sentence__RuletypeAssignment_0_2 : ( ( rule__Sentence__RuletypeAlternatives_0_2_0 ) ) ;
    public final void rule__Sentence__RuletypeAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3639:1: ( ( ( rule__Sentence__RuletypeAlternatives_0_2_0 ) ) )
            // InternalArchcnl.g:3640:2: ( ( rule__Sentence__RuletypeAlternatives_0_2_0 ) )
            {
            // InternalArchcnl.g:3640:2: ( ( rule__Sentence__RuletypeAlternatives_0_2_0 ) )
            // InternalArchcnl.g:3641:3: ( rule__Sentence__RuletypeAlternatives_0_2_0 )
            {
             before(grammarAccess.getSentenceAccess().getRuletypeAlternatives_0_2_0()); 
            // InternalArchcnl.g:3642:3: ( rule__Sentence__RuletypeAlternatives_0_2_0 )
            // InternalArchcnl.g:3642:4: rule__Sentence__RuletypeAlternatives_0_2_0
            {
            pushFollow(FOLLOW_2);
            rule__Sentence__RuletypeAlternatives_0_2_0();

            state._fsp--;


            }

             after(grammarAccess.getSentenceAccess().getRuletypeAlternatives_0_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__RuletypeAssignment_0_2"


    // $ANTLR start "rule__Sentence__RuletypeAssignment_1"
    // InternalArchcnl.g:3650:1: rule__Sentence__RuletypeAssignment_1 : ( ( rule__Sentence__RuletypeAlternatives_1_0 ) ) ;
    public final void rule__Sentence__RuletypeAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3654:1: ( ( ( rule__Sentence__RuletypeAlternatives_1_0 ) ) )
            // InternalArchcnl.g:3655:2: ( ( rule__Sentence__RuletypeAlternatives_1_0 ) )
            {
            // InternalArchcnl.g:3655:2: ( ( rule__Sentence__RuletypeAlternatives_1_0 ) )
            // InternalArchcnl.g:3656:3: ( rule__Sentence__RuletypeAlternatives_1_0 )
            {
             before(grammarAccess.getSentenceAccess().getRuletypeAlternatives_1_0()); 
            // InternalArchcnl.g:3657:3: ( rule__Sentence__RuletypeAlternatives_1_0 )
            // InternalArchcnl.g:3657:4: rule__Sentence__RuletypeAlternatives_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Sentence__RuletypeAlternatives_1_0();

            state._fsp--;


            }

             after(grammarAccess.getSentenceAccess().getRuletypeAlternatives_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__RuletypeAssignment_1"


    // $ANTLR start "rule__Sentence__FactsAssignment_2"
    // InternalArchcnl.g:3665:1: rule__Sentence__FactsAssignment_2 : ( ruleFactStatement ) ;
    public final void rule__Sentence__FactsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3669:1: ( ( ruleFactStatement ) )
            // InternalArchcnl.g:3670:2: ( ruleFactStatement )
            {
            // InternalArchcnl.g:3670:2: ( ruleFactStatement )
            // InternalArchcnl.g:3671:3: ruleFactStatement
            {
             before(grammarAccess.getSentenceAccess().getFactsFactStatementParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleFactStatement();

            state._fsp--;

             after(grammarAccess.getSentenceAccess().getFactsFactStatementParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sentence__FactsAssignment_2"


    // $ANTLR start "rule__NegationRuleType__SubjectAssignment_1_0_1"
    // InternalArchcnl.g:3680:1: rule__NegationRuleType__SubjectAssignment_1_0_1 : ( ruleConceptExpression ) ;
    public final void rule__NegationRuleType__SubjectAssignment_1_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3684:1: ( ( ruleConceptExpression ) )
            // InternalArchcnl.g:3685:2: ( ruleConceptExpression )
            {
            // InternalArchcnl.g:3685:2: ( ruleConceptExpression )
            // InternalArchcnl.g:3686:3: ruleConceptExpression
            {
             before(grammarAccess.getNegationRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0_1_0()); 
            pushFollow(FOLLOW_2);
            ruleConceptExpression();

            state._fsp--;

             after(grammarAccess.getNegationRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__SubjectAssignment_1_0_1"


    // $ANTLR start "rule__NegationRuleType__ObjectAssignment_1_0_3"
    // InternalArchcnl.g:3695:1: rule__NegationRuleType__ObjectAssignment_1_0_3 : ( ruleObject ) ;
    public final void rule__NegationRuleType__ObjectAssignment_1_0_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3699:1: ( ( ruleObject ) )
            // InternalArchcnl.g:3700:2: ( ruleObject )
            {
            // InternalArchcnl.g:3700:2: ( ruleObject )
            // InternalArchcnl.g:3701:3: ruleObject
            {
             before(grammarAccess.getNegationRuleTypeAccess().getObjectObjectParserRuleCall_1_0_3_0()); 
            pushFollow(FOLLOW_2);
            ruleObject();

            state._fsp--;

             after(grammarAccess.getNegationRuleTypeAccess().getObjectObjectParserRuleCall_1_0_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NegationRuleType__ObjectAssignment_1_0_3"


    // $ANTLR start "rule__Anything__RelationAssignment_0"
    // InternalArchcnl.g:3710:1: rule__Anything__RelationAssignment_0 : ( ruleRelation ) ;
    public final void rule__Anything__RelationAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3714:1: ( ( ruleRelation ) )
            // InternalArchcnl.g:3715:2: ( ruleRelation )
            {
            // InternalArchcnl.g:3715:2: ( ruleRelation )
            // InternalArchcnl.g:3716:3: ruleRelation
            {
             before(grammarAccess.getAnythingAccess().getRelationRelationParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleRelation();

            state._fsp--;

             after(grammarAccess.getAnythingAccess().getRelationRelationParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Anything__RelationAssignment_0"


    // $ANTLR start "rule__Nothing__ObjectAssignment_2"
    // InternalArchcnl.g:3725:1: rule__Nothing__ObjectAssignment_2 : ( ruleObject ) ;
    public final void rule__Nothing__ObjectAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3729:1: ( ( ruleObject ) )
            // InternalArchcnl.g:3730:2: ( ruleObject )
            {
            // InternalArchcnl.g:3730:2: ( ruleObject )
            // InternalArchcnl.g:3731:3: ruleObject
            {
             before(grammarAccess.getNothingAccess().getObjectObjectParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleObject();

            state._fsp--;

             after(grammarAccess.getNothingAccess().getObjectObjectParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Nothing__ObjectAssignment_2"


    // $ANTLR start "rule__ConditionalRuleType__StartAssignment_0"
    // InternalArchcnl.g:3740:1: rule__ConditionalRuleType__StartAssignment_0 : ( ( 'If' ) ) ;
    public final void rule__ConditionalRuleType__StartAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3744:1: ( ( ( 'If' ) ) )
            // InternalArchcnl.g:3745:2: ( ( 'If' ) )
            {
            // InternalArchcnl.g:3745:2: ( ( 'If' ) )
            // InternalArchcnl.g:3746:3: ( 'If' )
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getStartIfKeyword_0_0()); 
            // InternalArchcnl.g:3747:3: ( 'If' )
            // InternalArchcnl.g:3748:4: 'If'
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getStartIfKeyword_0_0()); 
            match(input,38,FOLLOW_2); 
             after(grammarAccess.getConditionalRuleTypeAccess().getStartIfKeyword_0_0()); 

            }

             after(grammarAccess.getConditionalRuleTypeAccess().getStartIfKeyword_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__StartAssignment_0"


    // $ANTLR start "rule__ConditionalRuleType__SubjectAssignment_1"
    // InternalArchcnl.g:3759:1: rule__ConditionalRuleType__SubjectAssignment_1 : ( ruleConceptExpression ) ;
    public final void rule__ConditionalRuleType__SubjectAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3763:1: ( ( ruleConceptExpression ) )
            // InternalArchcnl.g:3764:2: ( ruleConceptExpression )
            {
            // InternalArchcnl.g:3764:2: ( ruleConceptExpression )
            // InternalArchcnl.g:3765:3: ruleConceptExpression
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleConceptExpression();

            state._fsp--;

             after(grammarAccess.getConditionalRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__SubjectAssignment_1"


    // $ANTLR start "rule__ConditionalRuleType__RelationAssignment_2"
    // InternalArchcnl.g:3774:1: rule__ConditionalRuleType__RelationAssignment_2 : ( ruleRelation ) ;
    public final void rule__ConditionalRuleType__RelationAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3778:1: ( ( ruleRelation ) )
            // InternalArchcnl.g:3779:2: ( ruleRelation )
            {
            // InternalArchcnl.g:3779:2: ( ruleRelation )
            // InternalArchcnl.g:3780:3: ruleRelation
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getRelationRelationParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleRelation();

            state._fsp--;

             after(grammarAccess.getConditionalRuleTypeAccess().getRelationRelationParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__RelationAssignment_2"


    // $ANTLR start "rule__ConditionalRuleType__ObjectAssignment_3"
    // InternalArchcnl.g:3789:1: rule__ConditionalRuleType__ObjectAssignment_3 : ( ruleConceptExpression ) ;
    public final void rule__ConditionalRuleType__ObjectAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3793:1: ( ( ruleConceptExpression ) )
            // InternalArchcnl.g:3794:2: ( ruleConceptExpression )
            {
            // InternalArchcnl.g:3794:2: ( ruleConceptExpression )
            // InternalArchcnl.g:3795:3: ruleConceptExpression
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getObjectConceptExpressionParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleConceptExpression();

            state._fsp--;

             after(grammarAccess.getConditionalRuleTypeAccess().getObjectConceptExpressionParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__ObjectAssignment_3"


    // $ANTLR start "rule__ConditionalRuleType__Relation2Assignment_8"
    // InternalArchcnl.g:3804:1: rule__ConditionalRuleType__Relation2Assignment_8 : ( ruleRelation ) ;
    public final void rule__ConditionalRuleType__Relation2Assignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3808:1: ( ( ruleRelation ) )
            // InternalArchcnl.g:3809:2: ( ruleRelation )
            {
            // InternalArchcnl.g:3809:2: ( ruleRelation )
            // InternalArchcnl.g:3810:3: ruleRelation
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getRelation2RelationParserRuleCall_8_0()); 
            pushFollow(FOLLOW_2);
            ruleRelation();

            state._fsp--;

             after(grammarAccess.getConditionalRuleTypeAccess().getRelation2RelationParserRuleCall_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Relation2Assignment_8"


    // $ANTLR start "rule__ConditionalRuleType__Object2Assignment_10"
    // InternalArchcnl.g:3819:1: rule__ConditionalRuleType__Object2Assignment_10 : ( ruleConceptExpression ) ;
    public final void rule__ConditionalRuleType__Object2Assignment_10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3823:1: ( ( ruleConceptExpression ) )
            // InternalArchcnl.g:3824:2: ( ruleConceptExpression )
            {
            // InternalArchcnl.g:3824:2: ( ruleConceptExpression )
            // InternalArchcnl.g:3825:3: ruleConceptExpression
            {
             before(grammarAccess.getConditionalRuleTypeAccess().getObject2ConceptExpressionParserRuleCall_10_0()); 
            pushFollow(FOLLOW_2);
            ruleConceptExpression();

            state._fsp--;

             after(grammarAccess.getConditionalRuleTypeAccess().getObject2ConceptExpressionParserRuleCall_10_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConditionalRuleType__Object2Assignment_10"


    // $ANTLR start "rule__OnlyCanRuleType__StartAssignment_0"
    // InternalArchcnl.g:3834:1: rule__OnlyCanRuleType__StartAssignment_0 : ( ( 'Only' ) ) ;
    public final void rule__OnlyCanRuleType__StartAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3838:1: ( ( ( 'Only' ) ) )
            // InternalArchcnl.g:3839:2: ( ( 'Only' ) )
            {
            // InternalArchcnl.g:3839:2: ( ( 'Only' ) )
            // InternalArchcnl.g:3840:3: ( 'Only' )
            {
             before(grammarAccess.getOnlyCanRuleTypeAccess().getStartOnlyKeyword_0_0()); 
            // InternalArchcnl.g:3841:3: ( 'Only' )
            // InternalArchcnl.g:3842:4: 'Only'
            {
             before(grammarAccess.getOnlyCanRuleTypeAccess().getStartOnlyKeyword_0_0()); 
            match(input,39,FOLLOW_2); 
             after(grammarAccess.getOnlyCanRuleTypeAccess().getStartOnlyKeyword_0_0()); 

            }

             after(grammarAccess.getOnlyCanRuleTypeAccess().getStartOnlyKeyword_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__StartAssignment_0"


    // $ANTLR start "rule__OnlyCanRuleType__SubjectAssignment_1"
    // InternalArchcnl.g:3853:1: rule__OnlyCanRuleType__SubjectAssignment_1 : ( ruleConceptExpression ) ;
    public final void rule__OnlyCanRuleType__SubjectAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3857:1: ( ( ruleConceptExpression ) )
            // InternalArchcnl.g:3858:2: ( ruleConceptExpression )
            {
            // InternalArchcnl.g:3858:2: ( ruleConceptExpression )
            // InternalArchcnl.g:3859:3: ruleConceptExpression
            {
             before(grammarAccess.getOnlyCanRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleConceptExpression();

            state._fsp--;

             after(grammarAccess.getOnlyCanRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__SubjectAssignment_1"


    // $ANTLR start "rule__OnlyCanRuleType__ObjectAssignment_3"
    // InternalArchcnl.g:3868:1: rule__OnlyCanRuleType__ObjectAssignment_3 : ( ruleObject ) ;
    public final void rule__OnlyCanRuleType__ObjectAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3872:1: ( ( ruleObject ) )
            // InternalArchcnl.g:3873:2: ( ruleObject )
            {
            // InternalArchcnl.g:3873:2: ( ruleObject )
            // InternalArchcnl.g:3874:3: ruleObject
            {
             before(grammarAccess.getOnlyCanRuleTypeAccess().getObjectObjectParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleObject();

            state._fsp--;

             after(grammarAccess.getOnlyCanRuleTypeAccess().getObjectObjectParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OnlyCanRuleType__ObjectAssignment_3"


    // $ANTLR start "rule__SubConceptRuleType__ModifierAssignment_0"
    // InternalArchcnl.g:3883:1: rule__SubConceptRuleType__ModifierAssignment_0 : ( ( 'must' ) ) ;
    public final void rule__SubConceptRuleType__ModifierAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3887:1: ( ( ( 'must' ) ) )
            // InternalArchcnl.g:3888:2: ( ( 'must' ) )
            {
            // InternalArchcnl.g:3888:2: ( ( 'must' ) )
            // InternalArchcnl.g:3889:3: ( 'must' )
            {
             before(grammarAccess.getSubConceptRuleTypeAccess().getModifierMustKeyword_0_0()); 
            // InternalArchcnl.g:3890:3: ( 'must' )
            // InternalArchcnl.g:3891:4: 'must'
            {
             before(grammarAccess.getSubConceptRuleTypeAccess().getModifierMustKeyword_0_0()); 
            match(input,28,FOLLOW_2); 
             after(grammarAccess.getSubConceptRuleTypeAccess().getModifierMustKeyword_0_0()); 

            }

             after(grammarAccess.getSubConceptRuleTypeAccess().getModifierMustKeyword_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SubConceptRuleType__ModifierAssignment_0"


    // $ANTLR start "rule__SubConceptRuleType__ObjectAssignment_2"
    // InternalArchcnl.g:3902:1: rule__SubConceptRuleType__ObjectAssignment_2 : ( ruleConceptExpression ) ;
    public final void rule__SubConceptRuleType__ObjectAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3906:1: ( ( ruleConceptExpression ) )
            // InternalArchcnl.g:3907:2: ( ruleConceptExpression )
            {
            // InternalArchcnl.g:3907:2: ( ruleConceptExpression )
            // InternalArchcnl.g:3908:3: ruleConceptExpression
            {
             before(grammarAccess.getSubConceptRuleTypeAccess().getObjectConceptExpressionParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleConceptExpression();

            state._fsp--;

             after(grammarAccess.getSubConceptRuleTypeAccess().getObjectConceptExpressionParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SubConceptRuleType__ObjectAssignment_2"


    // $ANTLR start "rule__MustRuleType__ModifierAssignment_0"
    // InternalArchcnl.g:3917:1: rule__MustRuleType__ModifierAssignment_0 : ( ( 'must' ) ) ;
    public final void rule__MustRuleType__ModifierAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3921:1: ( ( ( 'must' ) ) )
            // InternalArchcnl.g:3922:2: ( ( 'must' ) )
            {
            // InternalArchcnl.g:3922:2: ( ( 'must' ) )
            // InternalArchcnl.g:3923:3: ( 'must' )
            {
             before(grammarAccess.getMustRuleTypeAccess().getModifierMustKeyword_0_0()); 
            // InternalArchcnl.g:3924:3: ( 'must' )
            // InternalArchcnl.g:3925:4: 'must'
            {
             before(grammarAccess.getMustRuleTypeAccess().getModifierMustKeyword_0_0()); 
            match(input,28,FOLLOW_2); 
             after(grammarAccess.getMustRuleTypeAccess().getModifierMustKeyword_0_0()); 

            }

             after(grammarAccess.getMustRuleTypeAccess().getModifierMustKeyword_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MustRuleType__ModifierAssignment_0"


    // $ANTLR start "rule__MustRuleType__ObjectAssignment_1"
    // InternalArchcnl.g:3936:1: rule__MustRuleType__ObjectAssignment_1 : ( ruleObject ) ;
    public final void rule__MustRuleType__ObjectAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3940:1: ( ( ruleObject ) )
            // InternalArchcnl.g:3941:2: ( ruleObject )
            {
            // InternalArchcnl.g:3941:2: ( ruleObject )
            // InternalArchcnl.g:3942:3: ruleObject
            {
             before(grammarAccess.getMustRuleTypeAccess().getObjectObjectParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleObject();

            state._fsp--;

             after(grammarAccess.getMustRuleTypeAccess().getObjectObjectParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MustRuleType__ObjectAssignment_1"


    // $ANTLR start "rule__Object__AnythingAssignment_0"
    // InternalArchcnl.g:3951:1: rule__Object__AnythingAssignment_0 : ( ruleAnything ) ;
    public final void rule__Object__AnythingAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3955:1: ( ( ruleAnything ) )
            // InternalArchcnl.g:3956:2: ( ruleAnything )
            {
            // InternalArchcnl.g:3956:2: ( ruleAnything )
            // InternalArchcnl.g:3957:3: ruleAnything
            {
             before(grammarAccess.getObjectAccess().getAnythingAnythingParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleAnything();

            state._fsp--;

             after(grammarAccess.getObjectAccess().getAnythingAnythingParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Object__AnythingAssignment_0"


    // $ANTLR start "rule__Object__ExpressionAssignment_1_0"
    // InternalArchcnl.g:3966:1: rule__Object__ExpressionAssignment_1_0 : ( ruleObjectConceptExpression ) ;
    public final void rule__Object__ExpressionAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3970:1: ( ( ruleObjectConceptExpression ) )
            // InternalArchcnl.g:3971:2: ( ruleObjectConceptExpression )
            {
            // InternalArchcnl.g:3971:2: ( ruleObjectConceptExpression )
            // InternalArchcnl.g:3972:3: ruleObjectConceptExpression
            {
             before(grammarAccess.getObjectAccess().getExpressionObjectConceptExpressionParserRuleCall_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleObjectConceptExpression();

            state._fsp--;

             after(grammarAccess.getObjectAccess().getExpressionObjectConceptExpressionParserRuleCall_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Object__ExpressionAssignment_1_0"


    // $ANTLR start "rule__Object__ObjectAndListAssignment_1_1_0"
    // InternalArchcnl.g:3981:1: rule__Object__ObjectAndListAssignment_1_1_0 : ( ruleAndObjectConceptExpression ) ;
    public final void rule__Object__ObjectAndListAssignment_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:3985:1: ( ( ruleAndObjectConceptExpression ) )
            // InternalArchcnl.g:3986:2: ( ruleAndObjectConceptExpression )
            {
            // InternalArchcnl.g:3986:2: ( ruleAndObjectConceptExpression )
            // InternalArchcnl.g:3987:3: ruleAndObjectConceptExpression
            {
             before(grammarAccess.getObjectAccess().getObjectAndListAndObjectConceptExpressionParserRuleCall_1_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleAndObjectConceptExpression();

            state._fsp--;

             after(grammarAccess.getObjectAccess().getObjectAndListAndObjectConceptExpressionParserRuleCall_1_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Object__ObjectAndListAssignment_1_1_0"


    // $ANTLR start "rule__Object__ObjectOrListAssignment_1_1_1"
    // InternalArchcnl.g:3996:1: rule__Object__ObjectOrListAssignment_1_1_1 : ( ruleOrObjectConceptExpression ) ;
    public final void rule__Object__ObjectOrListAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4000:1: ( ( ruleOrObjectConceptExpression ) )
            // InternalArchcnl.g:4001:2: ( ruleOrObjectConceptExpression )
            {
            // InternalArchcnl.g:4001:2: ( ruleOrObjectConceptExpression )
            // InternalArchcnl.g:4002:3: ruleOrObjectConceptExpression
            {
             before(grammarAccess.getObjectAccess().getObjectOrListOrObjectConceptExpressionParserRuleCall_1_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleOrObjectConceptExpression();

            state._fsp--;

             after(grammarAccess.getObjectAccess().getObjectOrListOrObjectConceptExpressionParserRuleCall_1_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Object__ObjectOrListAssignment_1_1_1"


    // $ANTLR start "rule__CanOnlyRuleType__ModifierAssignment_0"
    // InternalArchcnl.g:4011:1: rule__CanOnlyRuleType__ModifierAssignment_0 : ( ( 'can-only' ) ) ;
    public final void rule__CanOnlyRuleType__ModifierAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4015:1: ( ( ( 'can-only' ) ) )
            // InternalArchcnl.g:4016:2: ( ( 'can-only' ) )
            {
            // InternalArchcnl.g:4016:2: ( ( 'can-only' ) )
            // InternalArchcnl.g:4017:3: ( 'can-only' )
            {
             before(grammarAccess.getCanOnlyRuleTypeAccess().getModifierCanOnlyKeyword_0_0()); 
            // InternalArchcnl.g:4018:3: ( 'can-only' )
            // InternalArchcnl.g:4019:4: 'can-only'
            {
             before(grammarAccess.getCanOnlyRuleTypeAccess().getModifierCanOnlyKeyword_0_0()); 
            match(input,40,FOLLOW_2); 
             after(grammarAccess.getCanOnlyRuleTypeAccess().getModifierCanOnlyKeyword_0_0()); 

            }

             after(grammarAccess.getCanOnlyRuleTypeAccess().getModifierCanOnlyKeyword_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CanOnlyRuleType__ModifierAssignment_0"


    // $ANTLR start "rule__CanOnlyRuleType__ObjectAssignment_1"
    // InternalArchcnl.g:4030:1: rule__CanOnlyRuleType__ObjectAssignment_1 : ( ruleObject ) ;
    public final void rule__CanOnlyRuleType__ObjectAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4034:1: ( ( ruleObject ) )
            // InternalArchcnl.g:4035:2: ( ruleObject )
            {
            // InternalArchcnl.g:4035:2: ( ruleObject )
            // InternalArchcnl.g:4036:3: ruleObject
            {
             before(grammarAccess.getCanOnlyRuleTypeAccess().getObjectObjectParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleObject();

            state._fsp--;

             after(grammarAccess.getCanOnlyRuleTypeAccess().getObjectObjectParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CanOnlyRuleType__ObjectAssignment_1"


    // $ANTLR start "rule__CardinalityRuleType__ModiferAssignment_0"
    // InternalArchcnl.g:4045:1: rule__CardinalityRuleType__ModiferAssignment_0 : ( ( 'can' ) ) ;
    public final void rule__CardinalityRuleType__ModiferAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4049:1: ( ( ( 'can' ) ) )
            // InternalArchcnl.g:4050:2: ( ( 'can' ) )
            {
            // InternalArchcnl.g:4050:2: ( ( 'can' ) )
            // InternalArchcnl.g:4051:3: ( 'can' )
            {
             before(grammarAccess.getCardinalityRuleTypeAccess().getModiferCanKeyword_0_0()); 
            // InternalArchcnl.g:4052:3: ( 'can' )
            // InternalArchcnl.g:4053:4: 'can'
            {
             before(grammarAccess.getCardinalityRuleTypeAccess().getModiferCanKeyword_0_0()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getCardinalityRuleTypeAccess().getModiferCanKeyword_0_0()); 

            }

             after(grammarAccess.getCardinalityRuleTypeAccess().getModiferCanKeyword_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CardinalityRuleType__ModiferAssignment_0"


    // $ANTLR start "rule__CardinalityRuleType__ObjectAssignment_1"
    // InternalArchcnl.g:4064:1: rule__CardinalityRuleType__ObjectAssignment_1 : ( ruleObject ) ;
    public final void rule__CardinalityRuleType__ObjectAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4068:1: ( ( ruleObject ) )
            // InternalArchcnl.g:4069:2: ( ruleObject )
            {
            // InternalArchcnl.g:4069:2: ( ruleObject )
            // InternalArchcnl.g:4070:3: ruleObject
            {
             before(grammarAccess.getCardinalityRuleTypeAccess().getObjectObjectParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleObject();

            state._fsp--;

             after(grammarAccess.getCardinalityRuleTypeAccess().getObjectObjectParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CardinalityRuleType__ObjectAssignment_1"


    // $ANTLR start "rule__ObjectConceptExpression__RelationAssignment_0_0"
    // InternalArchcnl.g:4079:1: rule__ObjectConceptExpression__RelationAssignment_0_0 : ( ruleRelation ) ;
    public final void rule__ObjectConceptExpression__RelationAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4083:1: ( ( ruleRelation ) )
            // InternalArchcnl.g:4084:2: ( ruleRelation )
            {
            // InternalArchcnl.g:4084:2: ( ruleRelation )
            // InternalArchcnl.g:4085:3: ruleRelation
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getRelationRelationParserRuleCall_0_0_0()); 
            pushFollow(FOLLOW_2);
            ruleRelation();

            state._fsp--;

             after(grammarAccess.getObjectConceptExpressionAccess().getRelationRelationParserRuleCall_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__RelationAssignment_0_0"


    // $ANTLR start "rule__ObjectConceptExpression__CardinalityAssignment_0_1_0"
    // InternalArchcnl.g:4094:1: rule__ObjectConceptExpression__CardinalityAssignment_0_1_0 : ( ( rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0 ) ) ;
    public final void rule__ObjectConceptExpression__CardinalityAssignment_0_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4098:1: ( ( ( rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0 ) ) )
            // InternalArchcnl.g:4099:2: ( ( rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0 ) )
            {
            // InternalArchcnl.g:4099:2: ( ( rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0 ) )
            // InternalArchcnl.g:4100:3: ( rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0 )
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getCardinalityAlternatives_0_1_0_0()); 
            // InternalArchcnl.g:4101:3: ( rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0 )
            // InternalArchcnl.g:4101:4: rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0
            {
            pushFollow(FOLLOW_2);
            rule__ObjectConceptExpression__CardinalityAlternatives_0_1_0_0();

            state._fsp--;


            }

             after(grammarAccess.getObjectConceptExpressionAccess().getCardinalityAlternatives_0_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__CardinalityAssignment_0_1_0"


    // $ANTLR start "rule__ObjectConceptExpression__NumberAssignment_0_1_1"
    // InternalArchcnl.g:4109:1: rule__ObjectConceptExpression__NumberAssignment_0_1_1 : ( RULE_INT ) ;
    public final void rule__ObjectConceptExpression__NumberAssignment_0_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4113:1: ( ( RULE_INT ) )
            // InternalArchcnl.g:4114:2: ( RULE_INT )
            {
            // InternalArchcnl.g:4114:2: ( RULE_INT )
            // InternalArchcnl.g:4115:3: RULE_INT
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getNumberINTTerminalRuleCall_0_1_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getObjectConceptExpressionAccess().getNumberINTTerminalRuleCall_0_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__NumberAssignment_0_1_1"


    // $ANTLR start "rule__ObjectConceptExpression__ConceptAssignment_0_2"
    // InternalArchcnl.g:4124:1: rule__ObjectConceptExpression__ConceptAssignment_0_2 : ( ruleConceptExpression ) ;
    public final void rule__ObjectConceptExpression__ConceptAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4128:1: ( ( ruleConceptExpression ) )
            // InternalArchcnl.g:4129:2: ( ruleConceptExpression )
            {
            // InternalArchcnl.g:4129:2: ( ruleConceptExpression )
            // InternalArchcnl.g:4130:3: ruleConceptExpression
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getConceptConceptExpressionParserRuleCall_0_2_0()); 
            pushFollow(FOLLOW_2);
            ruleConceptExpression();

            state._fsp--;

             after(grammarAccess.getObjectConceptExpressionAccess().getConceptConceptExpressionParserRuleCall_0_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__ConceptAssignment_0_2"


    // $ANTLR start "rule__ObjectConceptExpression__RelationAssignment_1_0"
    // InternalArchcnl.g:4139:1: rule__ObjectConceptExpression__RelationAssignment_1_0 : ( ruleDatatypeRelation ) ;
    public final void rule__ObjectConceptExpression__RelationAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4143:1: ( ( ruleDatatypeRelation ) )
            // InternalArchcnl.g:4144:2: ( ruleDatatypeRelation )
            {
            // InternalArchcnl.g:4144:2: ( ruleDatatypeRelation )
            // InternalArchcnl.g:4145:3: ruleDatatypeRelation
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getRelationDatatypeRelationParserRuleCall_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleDatatypeRelation();

            state._fsp--;

             after(grammarAccess.getObjectConceptExpressionAccess().getRelationDatatypeRelationParserRuleCall_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__RelationAssignment_1_0"


    // $ANTLR start "rule__ObjectConceptExpression__DataAssignment_1_1"
    // InternalArchcnl.g:4154:1: rule__ObjectConceptExpression__DataAssignment_1_1 : ( ruleDataStatement ) ;
    public final void rule__ObjectConceptExpression__DataAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4158:1: ( ( ruleDataStatement ) )
            // InternalArchcnl.g:4159:2: ( ruleDataStatement )
            {
            // InternalArchcnl.g:4159:2: ( ruleDataStatement )
            // InternalArchcnl.g:4160:3: ruleDataStatement
            {
             before(grammarAccess.getObjectConceptExpressionAccess().getDataDataStatementParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDataStatement();

            state._fsp--;

             after(grammarAccess.getObjectConceptExpressionAccess().getDataDataStatementParserRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectConceptExpression__DataAssignment_1_1"


    // $ANTLR start "rule__AndObjectConceptExpression__ExpressionAssignment_1"
    // InternalArchcnl.g:4169:1: rule__AndObjectConceptExpression__ExpressionAssignment_1 : ( ruleObjectConceptExpression ) ;
    public final void rule__AndObjectConceptExpression__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4173:1: ( ( ruleObjectConceptExpression ) )
            // InternalArchcnl.g:4174:2: ( ruleObjectConceptExpression )
            {
            // InternalArchcnl.g:4174:2: ( ruleObjectConceptExpression )
            // InternalArchcnl.g:4175:3: ruleObjectConceptExpression
            {
             before(grammarAccess.getAndObjectConceptExpressionAccess().getExpressionObjectConceptExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleObjectConceptExpression();

            state._fsp--;

             after(grammarAccess.getAndObjectConceptExpressionAccess().getExpressionObjectConceptExpressionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndObjectConceptExpression__ExpressionAssignment_1"


    // $ANTLR start "rule__OrObjectConceptExpression__ExpressionAssignment_1"
    // InternalArchcnl.g:4184:1: rule__OrObjectConceptExpression__ExpressionAssignment_1 : ( ruleObjectConceptExpression ) ;
    public final void rule__OrObjectConceptExpression__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4188:1: ( ( ruleObjectConceptExpression ) )
            // InternalArchcnl.g:4189:2: ( ruleObjectConceptExpression )
            {
            // InternalArchcnl.g:4189:2: ( ruleObjectConceptExpression )
            // InternalArchcnl.g:4190:3: ruleObjectConceptExpression
            {
             before(grammarAccess.getOrObjectConceptExpressionAccess().getExpressionObjectConceptExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleObjectConceptExpression();

            state._fsp--;

             after(grammarAccess.getOrObjectConceptExpressionAccess().getExpressionObjectConceptExpressionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrObjectConceptExpression__ExpressionAssignment_1"


    // $ANTLR start "rule__DatatypeRelation__RelationNameAssignment_0"
    // InternalArchcnl.g:4199:1: rule__DatatypeRelation__RelationNameAssignment_0 : ( RULE_RELATION_NAME ) ;
    public final void rule__DatatypeRelation__RelationNameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4203:1: ( ( RULE_RELATION_NAME ) )
            // InternalArchcnl.g:4204:2: ( RULE_RELATION_NAME )
            {
            // InternalArchcnl.g:4204:2: ( RULE_RELATION_NAME )
            // InternalArchcnl.g:4205:3: RULE_RELATION_NAME
            {
             before(grammarAccess.getDatatypeRelationAccess().getRelationNameRELATION_NAMETerminalRuleCall_0_0()); 
            match(input,RULE_RELATION_NAME,FOLLOW_2); 
             after(grammarAccess.getDatatypeRelationAccess().getRelationNameRELATION_NAMETerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypeRelation__RelationNameAssignment_0"


    // $ANTLR start "rule__ObjectRelation__RelationNameAssignment"
    // InternalArchcnl.g:4214:1: rule__ObjectRelation__RelationNameAssignment : ( RULE_RELATION_NAME ) ;
    public final void rule__ObjectRelation__RelationNameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4218:1: ( ( RULE_RELATION_NAME ) )
            // InternalArchcnl.g:4219:2: ( RULE_RELATION_NAME )
            {
            // InternalArchcnl.g:4219:2: ( RULE_RELATION_NAME )
            // InternalArchcnl.g:4220:3: RULE_RELATION_NAME
            {
             before(grammarAccess.getObjectRelationAccess().getRelationNameRELATION_NAMETerminalRuleCall_0()); 
            match(input,RULE_RELATION_NAME,FOLLOW_2); 
             after(grammarAccess.getObjectRelationAccess().getRelationNameRELATION_NAMETerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectRelation__RelationNameAssignment"


    // $ANTLR start "rule__ConceptExpression__ConceptAssignment_1"
    // InternalArchcnl.g:4229:1: rule__ConceptExpression__ConceptAssignment_1 : ( ruleConcept ) ;
    public final void rule__ConceptExpression__ConceptAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4233:1: ( ( ruleConcept ) )
            // InternalArchcnl.g:4234:2: ( ruleConcept )
            {
            // InternalArchcnl.g:4234:2: ( ruleConcept )
            // InternalArchcnl.g:4235:3: ruleConcept
            {
             before(grammarAccess.getConceptExpressionAccess().getConceptConceptParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleConcept();

            state._fsp--;

             after(grammarAccess.getConceptExpressionAccess().getConceptConceptParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptExpression__ConceptAssignment_1"


    // $ANTLR start "rule__ConceptExpression__ThatAssignment_2"
    // InternalArchcnl.g:4244:1: rule__ConceptExpression__ThatAssignment_2 : ( ruleThatExpression ) ;
    public final void rule__ConceptExpression__ThatAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4248:1: ( ( ruleThatExpression ) )
            // InternalArchcnl.g:4249:2: ( ruleThatExpression )
            {
            // InternalArchcnl.g:4249:2: ( ruleThatExpression )
            // InternalArchcnl.g:4250:3: ruleThatExpression
            {
             before(grammarAccess.getConceptExpressionAccess().getThatThatExpressionParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleThatExpression();

            state._fsp--;

             after(grammarAccess.getConceptExpressionAccess().getThatThatExpressionParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptExpression__ThatAssignment_2"


    // $ANTLR start "rule__ThatExpression__ListAssignment_2"
    // InternalArchcnl.g:4259:1: rule__ThatExpression__ListAssignment_2 : ( ruleStatementList ) ;
    public final void rule__ThatExpression__ListAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4263:1: ( ( ruleStatementList ) )
            // InternalArchcnl.g:4264:2: ( ruleStatementList )
            {
            // InternalArchcnl.g:4264:2: ( ruleStatementList )
            // InternalArchcnl.g:4265:3: ruleStatementList
            {
             before(grammarAccess.getThatExpressionAccess().getListStatementListParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleStatementList();

            state._fsp--;

             after(grammarAccess.getThatExpressionAccess().getListStatementListParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ThatExpression__ListAssignment_2"


    // $ANTLR start "rule__StatementList__RelationAssignment_1_0"
    // InternalArchcnl.g:4274:1: rule__StatementList__RelationAssignment_1_0 : ( ruleRelation ) ;
    public final void rule__StatementList__RelationAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4278:1: ( ( ruleRelation ) )
            // InternalArchcnl.g:4279:2: ( ruleRelation )
            {
            // InternalArchcnl.g:4279:2: ( ruleRelation )
            // InternalArchcnl.g:4280:3: ruleRelation
            {
             before(grammarAccess.getStatementListAccess().getRelationRelationParserRuleCall_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleRelation();

            state._fsp--;

             after(grammarAccess.getStatementListAccess().getRelationRelationParserRuleCall_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__RelationAssignment_1_0"


    // $ANTLR start "rule__StatementList__ExpressionAssignment_1_1_0"
    // InternalArchcnl.g:4289:1: rule__StatementList__ExpressionAssignment_1_1_0 : ( ruleConceptExpression ) ;
    public final void rule__StatementList__ExpressionAssignment_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4293:1: ( ( ruleConceptExpression ) )
            // InternalArchcnl.g:4294:2: ( ruleConceptExpression )
            {
            // InternalArchcnl.g:4294:2: ( ruleConceptExpression )
            // InternalArchcnl.g:4295:3: ruleConceptExpression
            {
             before(grammarAccess.getStatementListAccess().getExpressionConceptExpressionParserRuleCall_1_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleConceptExpression();

            state._fsp--;

             after(grammarAccess.getStatementListAccess().getExpressionConceptExpressionParserRuleCall_1_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__ExpressionAssignment_1_1_0"


    // $ANTLR start "rule__StatementList__ExpressionAssignment_1_1_1"
    // InternalArchcnl.g:4304:1: rule__StatementList__ExpressionAssignment_1_1_1 : ( ruleDataStatement ) ;
    public final void rule__StatementList__ExpressionAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4308:1: ( ( ruleDataStatement ) )
            // InternalArchcnl.g:4309:2: ( ruleDataStatement )
            {
            // InternalArchcnl.g:4309:2: ( ruleDataStatement )
            // InternalArchcnl.g:4310:3: ruleDataStatement
            {
             before(grammarAccess.getStatementListAccess().getExpressionDataStatementParserRuleCall_1_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDataStatement();

            state._fsp--;

             after(grammarAccess.getStatementListAccess().getExpressionDataStatementParserRuleCall_1_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__ExpressionAssignment_1_1_1"


    // $ANTLR start "rule__StatementList__ExpressionAssignment_1_1_2"
    // InternalArchcnl.g:4319:1: rule__StatementList__ExpressionAssignment_1_1_2 : ( ruleVariableStatement ) ;
    public final void rule__StatementList__ExpressionAssignment_1_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4323:1: ( ( ruleVariableStatement ) )
            // InternalArchcnl.g:4324:2: ( ruleVariableStatement )
            {
            // InternalArchcnl.g:4324:2: ( ruleVariableStatement )
            // InternalArchcnl.g:4325:3: ruleVariableStatement
            {
             before(grammarAccess.getStatementListAccess().getExpressionVariableStatementParserRuleCall_1_1_2_0()); 
            pushFollow(FOLLOW_2);
            ruleVariableStatement();

            state._fsp--;

             after(grammarAccess.getStatementListAccess().getExpressionVariableStatementParserRuleCall_1_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StatementList__ExpressionAssignment_1_1_2"


    // $ANTLR start "rule__VariableStatement__ConceptAssignment_0"
    // InternalArchcnl.g:4334:1: rule__VariableStatement__ConceptAssignment_0 : ( ruleConcept ) ;
    public final void rule__VariableStatement__ConceptAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4338:1: ( ( ruleConcept ) )
            // InternalArchcnl.g:4339:2: ( ruleConcept )
            {
            // InternalArchcnl.g:4339:2: ( ruleConcept )
            // InternalArchcnl.g:4340:3: ruleConcept
            {
             before(grammarAccess.getVariableStatementAccess().getConceptConceptParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleConcept();

            state._fsp--;

             after(grammarAccess.getVariableStatementAccess().getConceptConceptParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableStatement__ConceptAssignment_0"


    // $ANTLR start "rule__VariableStatement__VariableAssignment_1"
    // InternalArchcnl.g:4349:1: rule__VariableStatement__VariableAssignment_1 : ( ruleVariable ) ;
    public final void rule__VariableStatement__VariableAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4353:1: ( ( ruleVariable ) )
            // InternalArchcnl.g:4354:2: ( ruleVariable )
            {
            // InternalArchcnl.g:4354:2: ( ruleVariable )
            // InternalArchcnl.g:4355:3: ruleVariable
            {
             before(grammarAccess.getVariableStatementAccess().getVariableVariableParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVariable();

            state._fsp--;

             after(grammarAccess.getVariableStatementAccess().getVariableVariableParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableStatement__VariableAssignment_1"


    // $ANTLR start "rule__DataStatement__StringValueAssignment_0"
    // InternalArchcnl.g:4364:1: rule__DataStatement__StringValueAssignment_0 : ( RULE_STRING ) ;
    public final void rule__DataStatement__StringValueAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4368:1: ( ( RULE_STRING ) )
            // InternalArchcnl.g:4369:2: ( RULE_STRING )
            {
            // InternalArchcnl.g:4369:2: ( RULE_STRING )
            // InternalArchcnl.g:4370:3: RULE_STRING
            {
             before(grammarAccess.getDataStatementAccess().getStringValueSTRINGTerminalRuleCall_0_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getDataStatementAccess().getStringValueSTRINGTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataStatement__StringValueAssignment_0"


    // $ANTLR start "rule__DataStatement__IntValueAssignment_1"
    // InternalArchcnl.g:4379:1: rule__DataStatement__IntValueAssignment_1 : ( RULE_INT ) ;
    public final void rule__DataStatement__IntValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4383:1: ( ( RULE_INT ) )
            // InternalArchcnl.g:4384:2: ( RULE_INT )
            {
            // InternalArchcnl.g:4384:2: ( RULE_INT )
            // InternalArchcnl.g:4385:3: RULE_INT
            {
             before(grammarAccess.getDataStatementAccess().getIntValueINTTerminalRuleCall_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDataStatementAccess().getIntValueINTTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataStatement__IntValueAssignment_1"


    // $ANTLR start "rule__FactStatement__AssertionAssignment_0_0"
    // InternalArchcnl.g:4394:1: rule__FactStatement__AssertionAssignment_0_0 : ( ruleConceptAssertion ) ;
    public final void rule__FactStatement__AssertionAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4398:1: ( ( ruleConceptAssertion ) )
            // InternalArchcnl.g:4399:2: ( ruleConceptAssertion )
            {
            // InternalArchcnl.g:4399:2: ( ruleConceptAssertion )
            // InternalArchcnl.g:4400:3: ruleConceptAssertion
            {
             before(grammarAccess.getFactStatementAccess().getAssertionConceptAssertionParserRuleCall_0_0_0()); 
            pushFollow(FOLLOW_2);
            ruleConceptAssertion();

            state._fsp--;

             after(grammarAccess.getFactStatementAccess().getAssertionConceptAssertionParserRuleCall_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FactStatement__AssertionAssignment_0_0"


    // $ANTLR start "rule__FactStatement__AssertionAssignment_0_1"
    // InternalArchcnl.g:4409:1: rule__FactStatement__AssertionAssignment_0_1 : ( ruleRoleAssertion ) ;
    public final void rule__FactStatement__AssertionAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4413:1: ( ( ruleRoleAssertion ) )
            // InternalArchcnl.g:4414:2: ( ruleRoleAssertion )
            {
            // InternalArchcnl.g:4414:2: ( ruleRoleAssertion )
            // InternalArchcnl.g:4415:3: ruleRoleAssertion
            {
             before(grammarAccess.getFactStatementAccess().getAssertionRoleAssertionParserRuleCall_0_1_0()); 
            pushFollow(FOLLOW_2);
            ruleRoleAssertion();

            state._fsp--;

             after(grammarAccess.getFactStatementAccess().getAssertionRoleAssertionParserRuleCall_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FactStatement__AssertionAssignment_0_1"


    // $ANTLR start "rule__ConceptAssertion__IndividualAssignment_0"
    // InternalArchcnl.g:4424:1: rule__ConceptAssertion__IndividualAssignment_0 : ( RULE_NAME ) ;
    public final void rule__ConceptAssertion__IndividualAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4428:1: ( ( RULE_NAME ) )
            // InternalArchcnl.g:4429:2: ( RULE_NAME )
            {
            // InternalArchcnl.g:4429:2: ( RULE_NAME )
            // InternalArchcnl.g:4430:3: RULE_NAME
            {
             before(grammarAccess.getConceptAssertionAccess().getIndividualNAMETerminalRuleCall_0_0()); 
            match(input,RULE_NAME,FOLLOW_2); 
             after(grammarAccess.getConceptAssertionAccess().getIndividualNAMETerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__IndividualAssignment_0"


    // $ANTLR start "rule__ConceptAssertion__ConceptAssignment_3"
    // InternalArchcnl.g:4439:1: rule__ConceptAssertion__ConceptAssignment_3 : ( ruleConcept ) ;
    public final void rule__ConceptAssertion__ConceptAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4443:1: ( ( ruleConcept ) )
            // InternalArchcnl.g:4444:2: ( ruleConcept )
            {
            // InternalArchcnl.g:4444:2: ( ruleConcept )
            // InternalArchcnl.g:4445:3: ruleConcept
            {
             before(grammarAccess.getConceptAssertionAccess().getConceptConceptParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleConcept();

            state._fsp--;

             after(grammarAccess.getConceptAssertionAccess().getConceptConceptParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ConceptAssertion__ConceptAssignment_3"


    // $ANTLR start "rule__DatatypePropertyAssertion__IndividualAssignment_0"
    // InternalArchcnl.g:4454:1: rule__DatatypePropertyAssertion__IndividualAssignment_0 : ( RULE_NAME ) ;
    public final void rule__DatatypePropertyAssertion__IndividualAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4458:1: ( ( RULE_NAME ) )
            // InternalArchcnl.g:4459:2: ( RULE_NAME )
            {
            // InternalArchcnl.g:4459:2: ( RULE_NAME )
            // InternalArchcnl.g:4460:3: RULE_NAME
            {
             before(grammarAccess.getDatatypePropertyAssertionAccess().getIndividualNAMETerminalRuleCall_0_0()); 
            match(input,RULE_NAME,FOLLOW_2); 
             after(grammarAccess.getDatatypePropertyAssertionAccess().getIndividualNAMETerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypePropertyAssertion__IndividualAssignment_0"


    // $ANTLR start "rule__DatatypePropertyAssertion__RelationAssignment_1"
    // InternalArchcnl.g:4469:1: rule__DatatypePropertyAssertion__RelationAssignment_1 : ( ruleDatatypeRelation ) ;
    public final void rule__DatatypePropertyAssertion__RelationAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4473:1: ( ( ruleDatatypeRelation ) )
            // InternalArchcnl.g:4474:2: ( ruleDatatypeRelation )
            {
            // InternalArchcnl.g:4474:2: ( ruleDatatypeRelation )
            // InternalArchcnl.g:4475:3: ruleDatatypeRelation
            {
             before(grammarAccess.getDatatypePropertyAssertionAccess().getRelationDatatypeRelationParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDatatypeRelation();

            state._fsp--;

             after(grammarAccess.getDatatypePropertyAssertionAccess().getRelationDatatypeRelationParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypePropertyAssertion__RelationAssignment_1"


    // $ANTLR start "rule__DatatypePropertyAssertion__StringAssignment_2_0"
    // InternalArchcnl.g:4484:1: rule__DatatypePropertyAssertion__StringAssignment_2_0 : ( RULE_STRING ) ;
    public final void rule__DatatypePropertyAssertion__StringAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4488:1: ( ( RULE_STRING ) )
            // InternalArchcnl.g:4489:2: ( RULE_STRING )
            {
            // InternalArchcnl.g:4489:2: ( RULE_STRING )
            // InternalArchcnl.g:4490:3: RULE_STRING
            {
             before(grammarAccess.getDatatypePropertyAssertionAccess().getStringSTRINGTerminalRuleCall_2_0_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getDatatypePropertyAssertionAccess().getStringSTRINGTerminalRuleCall_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypePropertyAssertion__StringAssignment_2_0"


    // $ANTLR start "rule__DatatypePropertyAssertion__IntAssignment_2_1"
    // InternalArchcnl.g:4499:1: rule__DatatypePropertyAssertion__IntAssignment_2_1 : ( RULE_INT ) ;
    public final void rule__DatatypePropertyAssertion__IntAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4503:1: ( ( RULE_INT ) )
            // InternalArchcnl.g:4504:2: ( RULE_INT )
            {
            // InternalArchcnl.g:4504:2: ( RULE_INT )
            // InternalArchcnl.g:4505:3: RULE_INT
            {
             before(grammarAccess.getDatatypePropertyAssertionAccess().getIntINTTerminalRuleCall_2_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDatatypePropertyAssertionAccess().getIntINTTerminalRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatatypePropertyAssertion__IntAssignment_2_1"


    // $ANTLR start "rule__ObjectPropertyAssertion__IndividualAssignment_0"
    // InternalArchcnl.g:4514:1: rule__ObjectPropertyAssertion__IndividualAssignment_0 : ( RULE_NAME ) ;
    public final void rule__ObjectPropertyAssertion__IndividualAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4518:1: ( ( RULE_NAME ) )
            // InternalArchcnl.g:4519:2: ( RULE_NAME )
            {
            // InternalArchcnl.g:4519:2: ( RULE_NAME )
            // InternalArchcnl.g:4520:3: RULE_NAME
            {
             before(grammarAccess.getObjectPropertyAssertionAccess().getIndividualNAMETerminalRuleCall_0_0()); 
            match(input,RULE_NAME,FOLLOW_2); 
             after(grammarAccess.getObjectPropertyAssertionAccess().getIndividualNAMETerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__IndividualAssignment_0"


    // $ANTLR start "rule__ObjectPropertyAssertion__RelationAssignment_1"
    // InternalArchcnl.g:4529:1: rule__ObjectPropertyAssertion__RelationAssignment_1 : ( ruleRelation ) ;
    public final void rule__ObjectPropertyAssertion__RelationAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4533:1: ( ( ruleRelation ) )
            // InternalArchcnl.g:4534:2: ( ruleRelation )
            {
            // InternalArchcnl.g:4534:2: ( ruleRelation )
            // InternalArchcnl.g:4535:3: ruleRelation
            {
             before(grammarAccess.getObjectPropertyAssertionAccess().getRelationRelationParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleRelation();

            state._fsp--;

             after(grammarAccess.getObjectPropertyAssertionAccess().getRelationRelationParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__RelationAssignment_1"


    // $ANTLR start "rule__ObjectPropertyAssertion__ConceptAssignment_3"
    // InternalArchcnl.g:4544:1: rule__ObjectPropertyAssertion__ConceptAssignment_3 : ( ruleConcept ) ;
    public final void rule__ObjectPropertyAssertion__ConceptAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4548:1: ( ( ruleConcept ) )
            // InternalArchcnl.g:4549:2: ( ruleConcept )
            {
            // InternalArchcnl.g:4549:2: ( ruleConcept )
            // InternalArchcnl.g:4550:3: ruleConcept
            {
             before(grammarAccess.getObjectPropertyAssertionAccess().getConceptConceptParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleConcept();

            state._fsp--;

             after(grammarAccess.getObjectPropertyAssertionAccess().getConceptConceptParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ObjectPropertyAssertion__ConceptAssignment_3"


    // $ANTLR start "rule__Concept__ConceptNameAssignment"
    // InternalArchcnl.g:4559:1: rule__Concept__ConceptNameAssignment : ( RULE_NAME ) ;
    public final void rule__Concept__ConceptNameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4563:1: ( ( RULE_NAME ) )
            // InternalArchcnl.g:4564:2: ( RULE_NAME )
            {
            // InternalArchcnl.g:4564:2: ( RULE_NAME )
            // InternalArchcnl.g:4565:3: RULE_NAME
            {
             before(grammarAccess.getConceptAccess().getConceptNameNAMETerminalRuleCall_0()); 
            match(input,RULE_NAME,FOLLOW_2); 
             after(grammarAccess.getConceptAccess().getConceptNameNAMETerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Concept__ConceptNameAssignment"


    // $ANTLR start "rule__Variable__NameAssignment"
    // InternalArchcnl.g:4574:1: rule__Variable__NameAssignment : ( RULE_VARIABLE_NAME ) ;
    public final void rule__Variable__NameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalArchcnl.g:4578:1: ( ( RULE_VARIABLE_NAME ) )
            // InternalArchcnl.g:4579:2: ( RULE_VARIABLE_NAME )
            {
            // InternalArchcnl.g:4579:2: ( RULE_VARIABLE_NAME )
            // InternalArchcnl.g:4580:3: RULE_VARIABLE_NAME
            {
             before(grammarAccess.getVariableAccess().getNameVARIABLE_NAMETerminalRuleCall_0()); 
            match(input,RULE_VARIABLE_NAME,FOLLOW_2); 
             after(grammarAccess.getVariableAccess().getNameVARIABLE_NAMETerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable__NameAssignment"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x000000C001280082L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000060080L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000010010400000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000180000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000180000002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x000000000007C080L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000080000020L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000080000022L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x00000000000600D0L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000060000L});

}