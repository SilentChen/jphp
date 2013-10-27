package ru.regenix.jphp.syntax;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import ru.regenix.jphp.exceptions.ParseException;
import ru.regenix.jphp.lexer.tokens.Token;
import ru.regenix.jphp.lexer.tokens.expr.ExprToken;
import ru.regenix.jphp.lexer.tokens.stmt.ExprStmtToken;
import ru.regenix.jphp.lexer.tokens.stmt.IfStmtToken;

import java.util.List;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IfSyntaxTest extends AbstractSyntaxTestCase {

    @Test
    public void testSimple(){
        List<Token> tree = getSyntaxTree("if (true) { } ");
        Assert.assertTrue(tree.size() == 1);
        Assert.assertTrue(tree.get(0) instanceof ExprStmtToken);
        Assert.assertTrue(((ExprStmtToken) tree.get(0)).getTokens().size() == 1);
        Assert.assertTrue(((ExprStmtToken) tree.get(0)).getTokens().get(0) instanceof IfStmtToken);

        IfStmtToken token = (IfStmtToken) ((ExprStmtToken) tree.get(0)).getTokens().get(0);
        Assert.assertNull(token.getBody());
        Assert.assertNotNull(token.getCondition());
    }

    @Test
    public void testShortly(){
        List<Token> tree = getSyntaxTree("if (true) 'string'; 123;");
        Assert.assertTrue(tree.size() == 2);
        Assert.assertTrue(tree.get(0) instanceof ExprStmtToken);
        Assert.assertTrue(((ExprStmtToken) tree.get(0)).getTokens().size() == 1);
        Assert.assertTrue(((ExprStmtToken) tree.get(0)).getTokens().get(0) instanceof IfStmtToken);

        IfStmtToken token = (IfStmtToken) ((ExprStmtToken) tree.get(0)).getTokens().get(0);
        Assert.assertNotNull(token.getBody());
        Assert.assertNotNull(token.getCondition());
    }

    @Test
    public void testAlternative(){
        List<Token> tree = getSyntaxTree("if (true): 123; endif");
        Assert.assertTrue(tree.size() == 1);
        Assert.assertTrue(tree.get(0) instanceof ExprStmtToken);
        Assert.assertTrue(((ExprStmtToken) tree.get(0)).getTokens().size() == 1);
        Assert.assertTrue(((ExprStmtToken) tree.get(0)).getTokens().get(0) instanceof IfStmtToken);

        IfStmtToken token = (IfStmtToken) ((ExprStmtToken) tree.get(0)).getTokens().get(0);
        Assert.assertNotNull(token.getBody());
        Assert.assertNotNull(token.getCondition());
    }

    @Test(expected = ParseException.class)
    public void testInvalidAlternative(){
        getSyntaxTree("if (true){ 123; endif");
    }

    @Test(expected = ParseException.class)
    public void testInvalidAlternative2(){
        getSyntaxTree("if (true): 123; }");
    }

    @Test
    public void testNested(){
        List<Token> tree = getSyntaxTree(
                "if (true){ " +
                        "'string'; " +
                        "if(false){ " +
                        "1234; " +
                        "} " +
                        "}");

        Assert.assertTrue(tree.size() == 1);
        Assert.assertTrue(tree.get(0) instanceof ExprStmtToken);
        Assert.assertTrue(((ExprStmtToken) tree.get(0)).getTokens().size() == 1);

        Assert.assertTrue(((ExprStmtToken) tree.get(0)).getTokens().get(0) instanceof IfStmtToken);
        IfStmtToken token = (IfStmtToken) ((ExprStmtToken) tree.get(0)).getTokens().get(0);
        Assert.assertNotNull(token.getBody());

        Assert.assertTrue(token.getBody().getInstructions().size() == 2);
        Assert.assertTrue(token.getBody().getInstructions().get(1).getTokens().size() == 1);
        Assert.assertTrue(token.getBody().getInstructions().get(1).getTokens().get(0) instanceof IfStmtToken);
    }
}
