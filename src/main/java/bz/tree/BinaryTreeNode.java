package bz.tree;

public final class BinaryTreeNode<T> {
    public BinaryTreeNode(T value) {
        this.value = value;
    }
    T value;
    BinaryTreeNode<T> left;
    BinaryTreeNode<T> right;
}
