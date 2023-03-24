package bz.tree;

public class Serialize {
    String serialize(BinaryTreeNode<Character> rootNode) {
        StringBuilder result = new StringBuilder();
        serialize(rootNode, result);
        return result.substring(0, result.length() - 1);
    }

    // 如果左右子节点都为null，需要用$表示。
    private void serialize(BinaryTreeNode<Character> rootNode, StringBuilder result) {
        if (rootNode == null) {
            result.append("$,");
            return;
        }
        result.append(rootNode.value + ",");
        serialize(rootNode.left, result);
        serialize(rootNode.right, result);
    }

    static class CharStream {
        int pos = 0;
        private String str;

        public CharStream(String str) {
            this.str = str;
        }

        static CharStream of(String str) {
            return new CharStream(str);
        }

        public Character next() {
            return str.charAt(pos++);
        }

        public boolean hasNext() {
            while (pos < str.length()) {
                char ch = str.charAt(pos);
                if (ch == ',') {
                    pos++;
                    continue;
                }
                if (ch == '$') {
                    pos++;
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }
    }

    BinaryTreeNode<Character> deserialize(String serializedStr) {
        return deserialize(CharStream.of(serializedStr));
    }

    private BinaryTreeNode<Character> deserialize(CharStream charStream) {
        BinaryTreeNode<Character> rootNode = null;
        if (charStream.hasNext()) {
            rootNode = new BinaryTreeNode<>(charStream.next());

            rootNode.left = deserialize(charStream);
            rootNode.right = deserialize(charStream);
        }

        return rootNode;
    }

}
