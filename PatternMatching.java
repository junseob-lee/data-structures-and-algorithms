import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author Junseob Lee
 * @version 1.0
 * @userid jlee3624
 * @GTID 903493189
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     *
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */

    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {

        if (pattern == null || pattern.length() == 0 || text == null || comparator == null) {
            throw new IllegalArgumentException("Invalid input: pattern, text, or comparator is null or empty.");
        }

        // Initialize the list to store the starting indices of matches
        List<Integer> list = new ArrayList<>();
        int i = 0;

        // 패턴이 아닌 텍스트 전체를 i로 순회
        while (i <= text.length() - pattern.length()) {
            int j = 0;

            // i로 순회할때마다 j 로 패턴이 맞는지 확인
            // j 가 패턴과 하나씩 비교하면서 같으면 j를 증가시킴
            while (j < pattern.length()
                    && comparator.compare(pattern.charAt(j), text.charAt(i + j)) == 0) {
                j++;
            }

            // 비교하며 같을때마다 같은 j 가 패턴 크기와 같으면 패턴을 찾았다는 뜻이니 list에 추가
            if (j == pattern.length()) {
                list.add(i);
            }

            // Move to the next substring in the text
            i++;
        }

        // Return the list of starting indices where the pattern is found in the text
        return list;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("Pattern or comparator is null");
        }
        // Initialize the table to store the failure table
        int[] table = new int[pattern.length()];
        // Check if the pattern is empty, if true, return the empty table
        if (pattern.length() == 0) {
            return table;
        }
        table[0] = 0;
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            // i 와 j 를 비교하면서 같으면 table[j] 에 i+1 을 저장하고 i 와 j 를 증가시킴
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                // i 와 j 가 같을때마다 1씩 증가 
                table[j] = i + 1;
                // i 와 j 를 증가시킴
                i++;
                j++;
            } else {
                // i 가 0 이고 매칭안되면 그냥 j 를 증가
                if (i == 0) {
                    table[j] = 0;
                    j++;
                // i 가 0 이 아닌상태에서 매칭이 안되면 또 제자리로 돌아감
                } else {
                    i = table[i - 1];
                }
            }
        }
        return table;
    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    // 1. Check if the pattern is null or has length 0.
    // 2. Check if text or comparator is null.
    // 3. Initialize 'indexList' to store starting indices of matches.
    // 4. Check if the pattern is longer than the text, if true, return 'indexList'.
    // 5. Build a failure table using buildFailureTable(pattern, comparator).
    // 6. Initialize 'index' to 0 and 'pIndex' to 0.
    // 7. Iterate through the text until 'index + pattern length' is beyond the text length.
    // 8. While iterating, find a full match or partial match between pattern and text.
    // 9. If a full match is found, add the starting index 'index' to 'indexList'.
    // 10. Update 'index' based on the failure table to shift to the next possible match.
    // 11. Return the 'indexList' containing all the starting indices of matches found.
    // 바로 mismatch 면 k 만 한칸 이동
    // mismatch j-1 인덱스가 0 이면 거기서부터 다시 시작
    // mismatch j-1 인덱스가 0 이 아니면 2이면 거기서 두칸 앞으로 가서 다시 시작 
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
       if (pattern == null || pattern.length() == 0 || text == null || comparator == null) {
            throw new IllegalArgumentException("Invalid input: pattern, text, or comparator is null or empty.");
        }        

        List<Integer> indexList = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return indexList;
        }

        int[] failureTable = buildFailureTable(pattern, comparator);
        int index = 0;
        int pIndex = 0;

        while (index + pattern.length() <= text.length()) {
            if (comparator.compare(pattern.charAt(pIndex), text.charAt(index + pIndex)) == 0) {
                if (pIndex == pattern.length() - 1) {
                    // Full match found
                    indexList.add(index);
                    // Update index to shift to the next possible match
                    index = index + pIndex - failureTable[pIndex];
                    // Reset pIndex to 0 to start matching from the beginning of the pattern
                    pIndex = 0;
                } else {
                    // Continue matching
                    pIndex++;
                }
            } else {
                // Mismatch
                if (pIndex == 0) {
                    // No partial match found, move to the next character in the text
                    index++;
                } else {
                    // Partial match found, update pIndex to continue from the failure table
                    pIndex = failureTable[pIndex - 1];
                }
            }
        }

        return indexList;
    }


    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    // letter 에 마지막 occurence 를 저장하는 table 을 만들어서 return
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }
        Map<Character, Integer> lastTable = new HashMap<>();
        // hashmap 안에서 자동으로 업데이트를 시켜줌
        for (int i = 0; i < pattern.length(); i++) {
            lastTable.put(pattern.charAt(i), i);
        }
        return lastTable;
    }
    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *

     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 || text == null || comparator == null) {
            throw new IllegalArgumentException("Invalid input: pattern, text, or comparator is null or empty.");
        }

        int patternLength = pattern.length();
        int textLength = text.length();

        // Check if the pattern is longer than the text, then there can be no matches.
        if (patternLength > textLength) {
            return new ArrayList<>();
        }

        Map<Character, Integer> lastTable = buildLastTable(pattern);
        List<Integer> matches = new ArrayList<>();

        int i = 0;
        while (i <= textLength - patternLength) {
            // 패턴의 마지막 문자부터 비교
            int j = patternLength - 1;
            // 패턴의 마지막 문자부터 비교하면서 같으면 j를 감소시킴
            while (j >= 0 && comparator.isEqual(text.charAt(i + j), pattern.charAt(j))) {
                j--;
            }
            // j 가 -1 이면 패턴을 찾았다는 뜻이니 list에 추가
            if (j == -1) {
                matches.add(i);
                i++;
            // j 가 -1 이 아니면 패턴을 찾지 못했다는 뜻이니 i 를 업데이트
            } else {
                // lastTable 에서 text.charAt(i + j) 를 찾아서 shift 를 업데이트. 사실상 shift 는 last occurence
                int shift = lastTable.getOrDefault(text.charAt(i + j), -1);
                // j 가 shift 보다 크면 i 를 j - shift 만큼 증가시킴
                if (shift < j) { 
                    // text 에 있는 문자가 pattern 에 없으면 -1 이니 i 를 j+1 만큼 증가시킴 즉 패턴만큼 움직이는 뜻 
                    i += j - shift;
                // j 가 shift 보다 작으면 i 를 1 증가시킴
                } else {
                    i++;
                }
            }
        }

        return matches;
    }

    /**
     * This method is OPTIONAL and for extra credit only.
     *
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     *
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                                    CharSequence text,
                                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 || text == null || comparator == null) {
            throw new IllegalArgumentException("Invalid input: pattern, text, or comparator is null or empty.");
        }
    
        List<Integer> list = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return list;
        }
        Map<Character, Integer> lastT = buildLastTable(pattern);
        int[] fail = buildFailureTable(pattern, comparator);
        // k 는 패턴의 길이 - fail[pattern.length() - 1]
        int k = pattern.length() - fail[pattern.length() - 1];
        // i 는 텍스트의 시작점
        int i = 0;
        // p 는 패턴의 시작점
        int p = 0;
        while (i <= text.length() - pattern.length()) {
            // 패턴의 마지막 문자부터 비교
            int j = pattern.length() - 1;
            while (j >= p && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            // j 가 p 보다 작으면 패턴을 찾았다는 뜻이니 list 에 추가
            if (j < p) {
                list.add(i);
                p = pattern.length() - k;
                i += k;
            } else {
                // j 가 p 보다 크면 패턴을 찾지 못했다는 뜻이니 i 를 업데이트
                p = 0;
                // lastT 에서 text.charAt(i + j) 를 찾아서 shift 를 업데이트. 사실상 shift 는 last occurence
                int move = lastT.getOrDefault(text.charAt(i + j), -1);
                // j 가 move 보다 크면 i 를 j - move 만큼 증가시킴
                if (j > move) {
                    i = i + j - move;
                } else {
                    i++;
                }
            }
        }
        return list;
    }
}
