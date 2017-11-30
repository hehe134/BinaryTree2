import org.junit.jupiter.api.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest {
    @Test
    void remove() {
        BinaryTree bst1 = new BinaryTree();
        BinaryTree<Integer> bst2 = new BinaryTree<>();
        BinaryTree<Integer> bst2t = new BinaryTree<>();
        BinaryTree<Integer> bst2t1 = new BinaryTree<>();
        BinaryTree<Integer> bst3 = new BinaryTree<>();
        BinaryTree<Integer> bst3t = new BinaryTree<>();
        BinaryTree<Integer> bst = new BinaryTree<>();
        bst.add(18);
        bst.add(6);
        bst.add(3);


        bst1.add(18);
        bst1.add(3);
        bst.remove(6);
        assertTrue(bst.isSameTree(bst.root, bst1.root));

        bst2.add(8);
        bst2.add(9);
        bst2.add(10);

        bst2t.add(8);
        bst2t.add(10);

        bst2.remove(9);
        assertTrue(bst.isSameTree(bst2.root, bst2t.root));//bst2:8,9,10->8,10
//         8              8
//            9     ->       10
//               10
        bst2t1.add(8);

        bst2.remove(10);
        assertTrue(bst2.isSameTree(bst2.root, bst2t1.root));//bst2:8,10 -> 8

        bst3.add(8);
        bst3.add(4);
        bst3.add(10);
        bst3.add(2);
        bst3.add(6);
        bst3.add(1);
        bst3.add(3);


        bst3t.add(8);
        bst3t.add(3);
        bst3t.add(10);
        bst3t.add(2);
        bst3t.add(6);
        bst3t.add(1);

        bst3.remove(4);
        assertTrue(bst3.isSameTree(bst3.root, bst3t.root));
//           8                                     8
//          /  \                                  / \
//     -> 4     10                               3  10
//       /  \                                   / \
//      2    6                remove 4         2   6
//     / \                     ----->         / \
//    1   3                                  1
    }

    @Test
    void subSet() {
        BinaryTree<Integer> t1 = new BinaryTree<>();
        t1.add(9);
        t1.add(10);
        t1.add(2);
        t1.add(3);
        t1.add(6);
        t1.add(15);
        SortedSet<Integer> t1test = new TreeSet<>();
        assertEquals(t1test, t1.subSet(2, 2));
        t1test.add(6);
        assertEquals(t1test, t1.subSet(3, 9));
        t1test.add(9);
        t1test.add(10);
        assertEquals(t1test, t1.subSet(3, 15));


        BinaryTree<Integer> t2 = new BinaryTree<>();
        t2.add(5);
        t2.add(10);
        t2.add(20);
        t2.add(13);
        t2.add(1);
        t2.add(9);
        t2.add(4);
        t2.add(11);
        SortedSet<Integer> t2test = new TreeSet<>();
        assertEquals(t2test, t2.subSet(9, 9));
        t2test.add(5);
        assertEquals(t2test, t2.subSet(4, 9));
        t2test.add(9);
        t2test.add(10);
        t2test.add(13);
        t2test.add(11);
        assertEquals(t2test, t2.subSet(4, 20));
    }
}
