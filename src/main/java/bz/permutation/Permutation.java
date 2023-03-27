package bz.permutation;

public class Permutation {
    public void permute(String str) {
        permute(new StringBuilder(str), 0);
    }

    public void permute(StringBuilder str, int begin) {
        if (begin == str.length()) {
            System.out.println(str);
            return;
        }

        for (int pos = begin; pos < str.length(); pos++) {
            swap(str, begin, pos);

            permute(str, begin + 1);

            swap(str, begin, pos);
        }
    }

    private void swap(StringBuilder str, int pos1, int pos2) {
        if (pos1 == pos2) {
            return;
        }
        char temp = str.charAt(pos1);
        str.setCharAt(pos1, str.charAt(pos2));
        str.setCharAt(pos2, temp);
    }
}
