package bz.tree;

import javax.xml.transform.sax.SAXTransformerFactory;

public class TreeUtils {
    /**
     * return a fixed tree.
     *            A
     *       B        C
     *   D     E    F
     *    G           H
     */
    public static BinaryTreeNode<Character> newBinaryTree() {
        BinaryTreeNode<Character> rootNode = new BinaryTreeNode<>('A');
        BinaryTreeNode<Character> bNode = new BinaryTreeNode<>('B');
        BinaryTreeNode<Character> cNode = new BinaryTreeNode<>('C');

        rootNode.left = bNode;
        rootNode.right = cNode;

        BinaryTreeNode<Character> dNode = new BinaryTreeNode<>('D');
        BinaryTreeNode<Character> eNode = new BinaryTreeNode<>('E');
        BinaryTreeNode<Character> fNode = new BinaryTreeNode<>('F');
        bNode.left = dNode;
        bNode.right = eNode;
        cNode.left = fNode;

        dNode.right = new BinaryTreeNode<>('G');
        fNode.right = new BinaryTreeNode<>('H');
        return rootNode;
    }

}
