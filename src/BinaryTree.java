
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {
        T value;

        Node<T> left = null;
        Node<T> parent = null;
        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
            closest.left.parent = closest;
        } else {
            assert closest.right == null;
            closest.right = newNode;
            closest.right.parent = closest;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private Node<T> max(Node<T> node) {
        Node<T> p = node;
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }

    @Override
    public boolean remove(Object o) {
        Node<T> p = find((T) o);
        if (p == null) return false;
        if (p.left == null && p.right == null) {
            Node<T> parentNode = p.parent;
            if (p == parentNode.left) parentNode.left = null;
            else parentNode.right = null;
            size--;
        } else if (p.left == null) {
            Node<T> parentNode = p.parent;
            if (p == parentNode.left) {
                parentNode.left = p.right;
                p.right.parent = parentNode;
            } else {
                parentNode.right = p.right;
                p.right.parent = parentNode;
            }
            size--;
        } else if (p.right == null) {
            Node<T> parentNode = p.parent;
            if (p == parentNode.left) {
                parentNode.left = p.left;
                p.left.parent = parentNode;
            } else {
                parentNode.right = p.left;
                p.left.parent = parentNode;
            }
            size--;
        } else {
            T a = max(p.left).value;
            remove(max(p.left).value);
            p.value = a;
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

//    public boolean contains1(Node<T> Node, T t) {
//
//        Node<T> closest = find(Node, t);
//        return closest != null && t.compareTo(closest.value) == 0;
//    }

    Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public boolean isSameTree(Node<T> p, Node<T> q) {
        if ((p == null && q != null) || (p != null && q == null)) {
            return false;
        }
        if (p != null && q != null) {
            if (!(p.value == q.value)) {
                return false;
            }
        }
        if (p == null && q == null) {

        } else {
            if (!isSameTree(p.left, q.left)) {
                return false;
            }
            if (!isSameTree(p.right, q.right)) {
                return false;
            }
        }

        return true;
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {
        }

        private Node<T> findNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) throws IllegalArgumentException{
//        throw new UnsupportedOperationException();
        SortedSet<T> sortedSet = new TreeSet<>();
        if (fromElement == toElement) return sortedSet;
        else {
            subset(fromElement, toElement, sortedSet,root);
            return sortedSet;
        }
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    public SortedSet<T> subset(T fromElement, T toElement, SortedSet<T> sortedSet, Node<T> Node) {
        if (Node==null) return sortedSet;
    //    fromElement < Node < toElement
        int a=fromElement.compareTo(Node.value);
        int b=Node.value.compareTo(toElement);
        if(a<0&&b<0){
            sortedSet.add(Node.value);
            subset(fromElement,toElement,sortedSet,Node.left);
            subset(fromElement,toElement,sortedSet,Node.right);
        }
        else if (a<0&&b>0){
            subset(fromElement,toElement,sortedSet,Node.left);
        }
        else if (a>0&&b<0){
            subset(fromElement,toElement,sortedSet,Node.right);
        }
        else if(a==0){subset(fromElement,toElement,sortedSet,Node.right);}
        else if(b==0)subset(fromElement,toElement,sortedSet,Node.left);
        return sortedSet;
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}