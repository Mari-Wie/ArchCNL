package org.architecture.cnl.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalArchcnlLexer extends Lexer {
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

    public InternalArchcnlLexer() {;} 
    public InternalArchcnlLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalArchcnlLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalArchcnl.g"; }

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:11:7: ( 'at-most' )
            // InternalArchcnl.g:11:9: 'at-most'
            {
            match("at-most"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:12:7: ( 'at-least' )
            // InternalArchcnl.g:12:9: 'at-least'
            {
            match("at-least"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:13:7: ( 'exactly' )
            // InternalArchcnl.g:13:9: 'exactly'
            {
            match("exactly"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:14:7: ( 'a' )
            // InternalArchcnl.g:14:9: 'a'
            {
            match('a'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:15:7: ( 'an' )
            // InternalArchcnl.g:15:9: 'an'
            {
            match("an"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:16:7: ( 'Every' )
            // InternalArchcnl.g:16:9: 'Every'
            {
            match("Every"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:17:7: ( '.' )
            // InternalArchcnl.g:17:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:18:7: ( 'No' )
            // InternalArchcnl.g:18:9: 'No'
            {
            match("No"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:19:7: ( 'can' )
            // InternalArchcnl.g:19:9: 'can'
            {
            match("can"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:20:7: ( 'anything' )
            // InternalArchcnl.g:20:9: 'anything'
            {
            match("anything"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:21:7: ( 'Nothing' )
            // InternalArchcnl.g:21:9: 'Nothing'
            {
            match("Nothing"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:22:7: ( ',' )
            // InternalArchcnl.g:22:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:23:7: ( 'then' )
            // InternalArchcnl.g:23:9: 'then'
            {
            match("then"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:24:7: ( 'it' )
            // InternalArchcnl.g:24:9: 'it'
            {
            match("it"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:25:7: ( 'must' )
            // InternalArchcnl.g:25:9: 'must'
            {
            match("must"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:26:7: ( 'this' )
            // InternalArchcnl.g:26:9: 'this'
            {
            match("this"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:27:7: ( 'be' )
            // InternalArchcnl.g:27:9: 'be'
            {
            match("be"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:28:7: ( 'and' )
            // InternalArchcnl.g:28:9: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:29:7: ( 'or' )
            // InternalArchcnl.g:29:9: 'or'
            {
            match("or"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:30:7: ( 'equal-to' )
            // InternalArchcnl.g:30:9: 'equal-to'
            {
            match("equal-to"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:31:7: ( 'that' )
            // InternalArchcnl.g:31:9: 'that'
            {
            match("that"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:32:7: ( '(' )
            // InternalArchcnl.g:32:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:33:7: ( ')' )
            // InternalArchcnl.g:33:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:34:7: ( 'is' )
            // InternalArchcnl.g:34:9: 'is'
            {
            match("is"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:35:7: ( 'If' )
            // InternalArchcnl.g:35:9: 'If'
            {
            match("If"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:36:7: ( 'Only' )
            // InternalArchcnl.g:36:9: 'Only'
            {
            match("Only"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:37:7: ( 'can-only' )
            // InternalArchcnl.g:37:9: 'can-only'
            {
            match("can-only"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "RULE_RELATION_NAME"
    public final void mRULE_RELATION_NAME() throws RecognitionException {
        try {
            int _type = RULE_RELATION_NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:4589:20: ( 'a' .. 'z' ( 'A' .. 'Z' | 'a' .. 'z' )+ )
            // InternalArchcnl.g:4589:22: 'a' .. 'z' ( 'A' .. 'Z' | 'a' .. 'z' )+
            {
            matchRange('a','z'); 
            // InternalArchcnl.g:4589:31: ( 'A' .. 'Z' | 'a' .. 'z' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='A' && LA1_0<='Z')||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalArchcnl.g:
            	    {
            	    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_RELATION_NAME"

    // $ANTLR start "RULE_NAME"
    public final void mRULE_NAME() throws RecognitionException {
        try {
            int _type = RULE_NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:4591:11: ( 'A' .. 'Z' ( 'A' .. 'Z' | 'a' .. 'z' )+ )
            // InternalArchcnl.g:4591:13: 'A' .. 'Z' ( 'A' .. 'Z' | 'a' .. 'z' )+
            {
            matchRange('A','Z'); 
            // InternalArchcnl.g:4591:22: ( 'A' .. 'Z' | 'a' .. 'z' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='A' && LA2_0<='Z')||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalArchcnl.g:
            	    {
            	    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_NAME"

    // $ANTLR start "RULE_VARIABLE_NAME"
    public final void mRULE_VARIABLE_NAME() throws RecognitionException {
        try {
            int _type = RULE_VARIABLE_NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:4593:20: ( 'A' .. 'Z' )
            // InternalArchcnl.g:4593:22: 'A' .. 'Z'
            {
            matchRange('A','Z'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_VARIABLE_NAME"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:4595:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalArchcnl.g:4595:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalArchcnl.g:4595:11: ( '^' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='^') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalArchcnl.g:4595:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalArchcnl.g:4595:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalArchcnl.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:4597:10: ( ( '0' .. '9' )+ )
            // InternalArchcnl.g:4597:12: ( '0' .. '9' )+
            {
            // InternalArchcnl.g:4597:12: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalArchcnl.g:4597:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:4599:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalArchcnl.g:4599:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalArchcnl.g:4599:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='\"') ) {
                alt8=1;
            }
            else if ( (LA8_0=='\'') ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalArchcnl.g:4599:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalArchcnl.g:4599:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop6:
                    do {
                        int alt6=3;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0=='\\') ) {
                            alt6=1;
                        }
                        else if ( ((LA6_0>='\u0000' && LA6_0<='!')||(LA6_0>='#' && LA6_0<='[')||(LA6_0>=']' && LA6_0<='\uFFFF')) ) {
                            alt6=2;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // InternalArchcnl.g:4599:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalArchcnl.g:4599:28: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalArchcnl.g:4599:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalArchcnl.g:4599:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop7:
                    do {
                        int alt7=3;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0=='\\') ) {
                            alt7=1;
                        }
                        else if ( ((LA7_0>='\u0000' && LA7_0<='&')||(LA7_0>='(' && LA7_0<='[')||(LA7_0>=']' && LA7_0<='\uFFFF')) ) {
                            alt7=2;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // InternalArchcnl.g:4599:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalArchcnl.g:4599:61: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:4601:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalArchcnl.g:4601:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalArchcnl.g:4601:24: ( options {greedy=false; } : . )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='*') ) {
                    int LA9_1 = input.LA(2);

                    if ( (LA9_1=='/') ) {
                        alt9=2;
                    }
                    else if ( ((LA9_1>='\u0000' && LA9_1<='.')||(LA9_1>='0' && LA9_1<='\uFFFF')) ) {
                        alt9=1;
                    }


                }
                else if ( ((LA9_0>='\u0000' && LA9_0<=')')||(LA9_0>='+' && LA9_0<='\uFFFF')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalArchcnl.g:4601:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:4603:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalArchcnl.g:4603:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalArchcnl.g:4603:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\u0000' && LA10_0<='\t')||(LA10_0>='\u000B' && LA10_0<='\f')||(LA10_0>='\u000E' && LA10_0<='\uFFFF')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalArchcnl.g:4603:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            // InternalArchcnl.g:4603:40: ( ( '\\r' )? '\\n' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='\n'||LA12_0=='\r') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalArchcnl.g:4603:41: ( '\\r' )? '\\n'
                    {
                    // InternalArchcnl.g:4603:41: ( '\\r' )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='\r') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalArchcnl.g:4603:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:4605:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalArchcnl.g:4605:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalArchcnl.g:4605:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='\t' && LA13_0<='\n')||LA13_0=='\r'||LA13_0==' ') ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalArchcnl.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalArchcnl.g:4607:16: ( . )
            // InternalArchcnl.g:4607:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalArchcnl.g:1:8: ( T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | RULE_RELATION_NAME | RULE_NAME | RULE_VARIABLE_NAME | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt14=37;
        alt14 = dfa14.predict(input);
        switch (alt14) {
            case 1 :
                // InternalArchcnl.g:1:10: T__14
                {
                mT__14(); 

                }
                break;
            case 2 :
                // InternalArchcnl.g:1:16: T__15
                {
                mT__15(); 

                }
                break;
            case 3 :
                // InternalArchcnl.g:1:22: T__16
                {
                mT__16(); 

                }
                break;
            case 4 :
                // InternalArchcnl.g:1:28: T__17
                {
                mT__17(); 

                }
                break;
            case 5 :
                // InternalArchcnl.g:1:34: T__18
                {
                mT__18(); 

                }
                break;
            case 6 :
                // InternalArchcnl.g:1:40: T__19
                {
                mT__19(); 

                }
                break;
            case 7 :
                // InternalArchcnl.g:1:46: T__20
                {
                mT__20(); 

                }
                break;
            case 8 :
                // InternalArchcnl.g:1:52: T__21
                {
                mT__21(); 

                }
                break;
            case 9 :
                // InternalArchcnl.g:1:58: T__22
                {
                mT__22(); 

                }
                break;
            case 10 :
                // InternalArchcnl.g:1:64: T__23
                {
                mT__23(); 

                }
                break;
            case 11 :
                // InternalArchcnl.g:1:70: T__24
                {
                mT__24(); 

                }
                break;
            case 12 :
                // InternalArchcnl.g:1:76: T__25
                {
                mT__25(); 

                }
                break;
            case 13 :
                // InternalArchcnl.g:1:82: T__26
                {
                mT__26(); 

                }
                break;
            case 14 :
                // InternalArchcnl.g:1:88: T__27
                {
                mT__27(); 

                }
                break;
            case 15 :
                // InternalArchcnl.g:1:94: T__28
                {
                mT__28(); 

                }
                break;
            case 16 :
                // InternalArchcnl.g:1:100: T__29
                {
                mT__29(); 

                }
                break;
            case 17 :
                // InternalArchcnl.g:1:106: T__30
                {
                mT__30(); 

                }
                break;
            case 18 :
                // InternalArchcnl.g:1:112: T__31
                {
                mT__31(); 

                }
                break;
            case 19 :
                // InternalArchcnl.g:1:118: T__32
                {
                mT__32(); 

                }
                break;
            case 20 :
                // InternalArchcnl.g:1:124: T__33
                {
                mT__33(); 

                }
                break;
            case 21 :
                // InternalArchcnl.g:1:130: T__34
                {
                mT__34(); 

                }
                break;
            case 22 :
                // InternalArchcnl.g:1:136: T__35
                {
                mT__35(); 

                }
                break;
            case 23 :
                // InternalArchcnl.g:1:142: T__36
                {
                mT__36(); 

                }
                break;
            case 24 :
                // InternalArchcnl.g:1:148: T__37
                {
                mT__37(); 

                }
                break;
            case 25 :
                // InternalArchcnl.g:1:154: T__38
                {
                mT__38(); 

                }
                break;
            case 26 :
                // InternalArchcnl.g:1:160: T__39
                {
                mT__39(); 

                }
                break;
            case 27 :
                // InternalArchcnl.g:1:166: T__40
                {
                mT__40(); 

                }
                break;
            case 28 :
                // InternalArchcnl.g:1:172: RULE_RELATION_NAME
                {
                mRULE_RELATION_NAME(); 

                }
                break;
            case 29 :
                // InternalArchcnl.g:1:191: RULE_NAME
                {
                mRULE_NAME(); 

                }
                break;
            case 30 :
                // InternalArchcnl.g:1:201: RULE_VARIABLE_NAME
                {
                mRULE_VARIABLE_NAME(); 

                }
                break;
            case 31 :
                // InternalArchcnl.g:1:220: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 32 :
                // InternalArchcnl.g:1:228: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 33 :
                // InternalArchcnl.g:1:237: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 34 :
                // InternalArchcnl.g:1:249: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 35 :
                // InternalArchcnl.g:1:265: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 36 :
                // InternalArchcnl.g:1:281: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 37 :
                // InternalArchcnl.g:1:289: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA14 dfa14 = new DFA14(this);
    static final String DFA14_eotS =
        "\1\uffff\1\37\1\36\1\44\1\uffff\1\44\1\36\1\uffff\5\36\2\uffff\2\44\1\36\1\44\1\32\2\uffff\3\32\2\uffff\1\71\1\74\1\71\2\uffff\2\71\2\100\2\uffff\1\102\1\71\1\uffff\1\71\1\107\1\110\1\71\1\112\1\113\2\uffff\1\114\1\100\7\uffff\1\71\1\121\1\uffff\2\71\1\100\1\uffff\1\100\1\uffff\1\127\3\71\2\uffff\1\71\3\uffff\1\100\2\uffff\1\71\1\uffff\2\71\2\100\2\uffff\1\142\1\143\1\144\1\145\1\146\3\71\1\152\1\100\5\uffff\2\71\2\uffff\1\100\1\71\1\160\1\161\1\162\3\uffff";
    static final String DFA14_eofS =
        "\163\uffff";
    static final String DFA14_minS =
        "\1\0\1\60\1\101\1\60\1\uffff\1\60\1\101\1\uffff\5\101\2\uffff\2\60\1\101\1\60\1\101\2\uffff\2\0\1\52\2\uffff\1\55\2\60\2\uffff\4\60\2\uffff\2\60\1\uffff\6\60\2\uffff\2\60\5\uffff\1\154\1\uffff\2\60\1\uffff\3\60\1\uffff\1\60\1\uffff\1\55\3\60\2\uffff\1\60\3\uffff\1\60\2\uffff\1\60\1\uffff\4\60\2\uffff\7\60\1\55\2\60\5\uffff\2\60\2\uffff\5\60\3\uffff";
    static final String DFA14_maxS =
        "\1\uffff\3\172\1\uffff\2\172\1\uffff\5\172\2\uffff\5\172\2\uffff\2\uffff\1\57\2\uffff\3\172\2\uffff\4\172\2\uffff\2\172\1\uffff\6\172\2\uffff\2\172\5\uffff\1\155\1\uffff\2\172\1\uffff\3\172\1\uffff\1\172\1\uffff\4\172\2\uffff\1\172\3\uffff\1\172\2\uffff\1\172\1\uffff\4\172\2\uffff\12\172\5\uffff\2\172\2\uffff\5\172\3\uffff";
    static final String DFA14_acceptS =
        "\4\uffff\1\7\2\uffff\1\14\5\uffff\1\26\1\27\5\uffff\1\37\1\40\3\uffff\1\44\1\45\3\uffff\1\37\1\4\4\uffff\1\36\1\7\2\uffff\1\14\6\uffff\1\26\1\27\2\uffff\1\40\1\41\1\42\1\43\1\44\1\uffff\1\34\2\uffff\1\5\3\uffff\1\35\1\uffff\1\10\4\uffff\1\16\1\30\1\uffff\1\21\1\23\1\31\1\uffff\1\1\1\2\1\uffff\1\22\4\uffff\1\33\1\11\12\uffff\1\15\1\20\1\25\1\17\1\32\2\uffff\1\24\1\6\5\uffff\1\3\1\13\1\12";
    static final String DFA14_specialS =
        "\1\0\25\uffff\1\2\1\1\133\uffff}>";
    static final String[] DFA14_transitionS = {
            "\11\32\2\31\2\32\1\31\22\32\1\31\1\32\1\26\4\32\1\27\1\15\1\16\2\32\1\7\1\32\1\4\1\30\12\25\7\32\4\22\1\3\3\22\1\17\4\22\1\5\1\20\13\22\3\32\1\23\1\24\1\32\1\1\1\13\1\6\1\21\1\2\3\21\1\11\3\21\1\12\1\21\1\14\4\21\1\10\6\21\uff85\32",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\15\35\1\34\5\35\1\33\6\35",
            "\32\35\6\uffff\20\35\1\41\6\35\1\40\2\35",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\25\43\1\42\4\43",
            "",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\16\43\1\46\13\43",
            "\32\35\6\uffff\1\47\31\35",
            "",
            "\32\35\6\uffff\7\35\1\51\22\35",
            "\32\35\6\uffff\22\35\1\53\1\52\6\35",
            "\32\35\6\uffff\24\35\1\54\5\35",
            "\32\35\6\uffff\4\35\1\55\25\35",
            "\32\35\6\uffff\21\35\1\56\10\35",
            "",
            "",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\5\43\1\61\24\43",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\15\43\1\62\14\43",
            "\32\35\6\uffff\32\35",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\32\43",
            "\32\36\4\uffff\1\36\1\uffff\32\36",
            "",
            "",
            "\0\64",
            "\0\64",
            "\1\65\4\uffff\1\66",
            "",
            "",
            "\1\70\2\uffff\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\3\35\1\73\24\35\1\72\1\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "",
            "",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\1\75\31\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\24\35\1\76\5\35",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\4\43\1\77\25\43",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\32\43",
            "",
            "",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\23\43\1\101\6\43",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\15\35\1\103\14\35",
            "",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\1\106\3\35\1\104\3\35\1\105\21\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\22\35\1\111\7\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "",
            "",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\32\43",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\13\43\1\115\16\43",
            "",
            "",
            "",
            "",
            "",
            "\1\117\1\116",
            "",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\23\35\1\120\6\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\2\35\1\122\27\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\1\123\31\35",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\21\43\1\124\10\43",
            "",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\7\43\1\125\22\43",
            "",
            "\1\126\2\uffff\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\15\35\1\130\14\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\22\35\1\131\7\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\23\35\1\132\6\35",
            "",
            "",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\23\35\1\133\6\35",
            "",
            "",
            "",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\30\43\1\134\1\43",
            "",
            "",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\7\35\1\135\22\35",
            "",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\23\35\1\136\6\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\13\35\1\137\16\35",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\30\43\1\140\1\43",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\10\43\1\141\21\43",
            "",
            "",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\32\43",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\10\35\1\147\21\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\13\35\1\150\16\35",
            "\1\151\2\uffff\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\32\43",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\15\43\1\153\14\43",
            "",
            "",
            "",
            "",
            "",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\15\35\1\154\14\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\30\35\1\155\1\35",
            "",
            "",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\6\43\1\156\23\43",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\6\35\1\157\23\35",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "\12\36\7\uffff\32\43\4\uffff\1\36\1\uffff\32\43",
            "\12\36\7\uffff\32\35\4\uffff\1\36\1\uffff\32\35",
            "",
            "",
            ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | RULE_RELATION_NAME | RULE_NAME | RULE_VARIABLE_NAME | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA14_0 = input.LA(1);

                        s = -1;
                        if ( (LA14_0=='a') ) {s = 1;}

                        else if ( (LA14_0=='e') ) {s = 2;}

                        else if ( (LA14_0=='E') ) {s = 3;}

                        else if ( (LA14_0=='.') ) {s = 4;}

                        else if ( (LA14_0=='N') ) {s = 5;}

                        else if ( (LA14_0=='c') ) {s = 6;}

                        else if ( (LA14_0==',') ) {s = 7;}

                        else if ( (LA14_0=='t') ) {s = 8;}

                        else if ( (LA14_0=='i') ) {s = 9;}

                        else if ( (LA14_0=='m') ) {s = 10;}

                        else if ( (LA14_0=='b') ) {s = 11;}

                        else if ( (LA14_0=='o') ) {s = 12;}

                        else if ( (LA14_0=='(') ) {s = 13;}

                        else if ( (LA14_0==')') ) {s = 14;}

                        else if ( (LA14_0=='I') ) {s = 15;}

                        else if ( (LA14_0=='O') ) {s = 16;}

                        else if ( (LA14_0=='d'||(LA14_0>='f' && LA14_0<='h')||(LA14_0>='j' && LA14_0<='l')||LA14_0=='n'||(LA14_0>='p' && LA14_0<='s')||(LA14_0>='u' && LA14_0<='z')) ) {s = 17;}

                        else if ( ((LA14_0>='A' && LA14_0<='D')||(LA14_0>='F' && LA14_0<='H')||(LA14_0>='J' && LA14_0<='M')||(LA14_0>='P' && LA14_0<='Z')) ) {s = 18;}

                        else if ( (LA14_0=='^') ) {s = 19;}

                        else if ( (LA14_0=='_') ) {s = 20;}

                        else if ( ((LA14_0>='0' && LA14_0<='9')) ) {s = 21;}

                        else if ( (LA14_0=='\"') ) {s = 22;}

                        else if ( (LA14_0=='\'') ) {s = 23;}

                        else if ( (LA14_0=='/') ) {s = 24;}

                        else if ( ((LA14_0>='\t' && LA14_0<='\n')||LA14_0=='\r'||LA14_0==' ') ) {s = 25;}

                        else if ( ((LA14_0>='\u0000' && LA14_0<='\b')||(LA14_0>='\u000B' && LA14_0<='\f')||(LA14_0>='\u000E' && LA14_0<='\u001F')||LA14_0=='!'||(LA14_0>='#' && LA14_0<='&')||(LA14_0>='*' && LA14_0<='+')||LA14_0=='-'||(LA14_0>=':' && LA14_0<='@')||(LA14_0>='[' && LA14_0<=']')||LA14_0=='`'||(LA14_0>='{' && LA14_0<='\uFFFF')) ) {s = 26;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA14_23 = input.LA(1);

                        s = -1;
                        if ( ((LA14_23>='\u0000' && LA14_23<='\uFFFF')) ) {s = 52;}

                        else s = 26;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA14_22 = input.LA(1);

                        s = -1;
                        if ( ((LA14_22>='\u0000' && LA14_22<='\uFFFF')) ) {s = 52;}

                        else s = 26;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 14, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}