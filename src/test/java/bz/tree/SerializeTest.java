package bz.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SerializeTest {
    static Serialize serialize;
    @BeforeAll
    static void inti() {
        serialize = new Serialize();
    }

    @Test
    public void testSerialize() {
        BinaryTreeNode<Character> rootNode = TreeUtils.newBinaryTree();
        String result = serialize.serialize(rootNode);

        Assertions.assertEquals(result, "A,B,D,$,G,$,$,E,$,$,C,F,$,H,$,$,$");

        rootNode = serialize.deserialize(result);

        Assertions.assertEquals(serialize.serialize(rootNode), "A,B,D,$,G,$,$,E,$,$,C,F,$,H,$,$,$");
    }

    @Test
    public void testSerialize2() {
        /**
         *            A
         *       B        C
         *         E    F   H
         *       G
         */
        String str = "A,B,$,E,G,$,$,$,C,F,$,$,H,$,$";
        BinaryTreeNode<Character> rootNode = serialize.deserialize(str);
        Assertions.assertEquals(str, serialize.serialize(rootNode));
    }
}
