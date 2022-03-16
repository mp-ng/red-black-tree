package redblacktree;

import java.util.NoSuchElementException;

public class RedBlackTree<K extends Comparable<? super K>, V> {

  Node<K, V> root;

  public RedBlackTree() {
    this.root = null;
  }

  RedBlackTree(Node<K, V> root) {
    this.root = root;
  }

  public void put(K key, V value) {

    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);

    Node<K, V> parent = pair.getX();
    Node<K, V> current = pair.getY();

    if (current != null) { // found an existing key
      current.setValue(value);
      return;
    }

    if (parent == null) { // empty tree
      root = new Node<>(key, value, Color.BLACK);
      return;
    }
    /* create a new key */
    int comparison = key.compareTo(parent.getKey());
    Node<K, V> newNode = new Node<>(key, value, Color.RED);
    if (comparison < 0) {
      // new value is less than parent
      parent.setLeft(newNode);
    } else {
      assert comparison > 0;
      // new value is more than parent
      parent.setRight(newNode);
    }

    insertCaseOne(newNode);
  }

  private void insertCaseOne(Node<K, V> current) {
    if (current == root) {
      current.setBlack();
    } else {
      insertCaseTwo(current);
    }
  }

  private void insertCaseTwo(Node<K, V> current) {
    if (current.getParent().isRed()) {
      insertCaseThree(current);
    }
  }

  private void insertCaseThree(Node<K, V> current) {
    if (current.getUncle() != null && current.getUncle().isRed()) {
      current.getParent().setBlack();
      current.getUncle().setBlack();
      assert current.getGrandparent() != null;
      current.getGrandparent().setRed();
      insertCaseOne(current.getGrandparent());
    } else {
      insertCaseFour(current);
    }
  }

  private void insertCaseFour(Node<K, V> current) {
    Node<K, V> parent = current.getParent();
    Node<K, V> grandparent = current.getGrandparent();
    assert grandparent != null;
    if (parent.isLeftChild() && current.isRightChild()) {
      parent.rotateLeft();
      insertCaseFive(parent);
    } else if (parent.isRightChild() && current.isLeftChild()) {
      parent.rotateRight();
      insertCaseFive(parent);
    } else {
      insertCaseFive(current);
    }
  }

  private void insertCaseFive(Node<K, V> current) {
    Node<K, V> parent = current.getParent();
    Node<K, V> grandparent = current.getGrandparent();
    boolean grandparentIsRoot = grandparent == root;
    Node<K, V> possibleNewRoot;

    parent.setBlack();
    assert grandparent != null;
    grandparent.setRed();

    if (current.isLeftChild()) {
      possibleNewRoot = grandparent.rotateRight();
    } else {
      possibleNewRoot = grandparent.rotateLeft();
    }
    if (grandparentIsRoot) {
      root = possibleNewRoot;
    }
  }

  private Tuple<Node<K, V>, Node<K, V>> findNode(K key) {
    Node<K, V> current = root;
    Node<K, V> parent = null;

    // traverse down the tree
    while (current != null) {
      parent = current;

      int comparison = key.compareTo(current.getKey());
      if (comparison < 0) {
        // new value is less than current
        current = current.getLeft();
      } else if (comparison == 0) {
        break;
      } else {
        // new value is more than current
        current = current.getRight();
      }
    }

    // current is the node we want, parent is its parent
    return new Tuple<>(parent, current);
  }

  public boolean contains(K key) {
    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);
    return pair.getY() != null;
  }

  public V get(K key) {
    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);
    Node<K, V> current = pair.getY();
    if (current == null) {
      throw new NoSuchElementException();
    }
    return current.getValue();
  }

  public void clear() {
    this.root = null;
  }

  public String toString() {
    return "RBT " + root + " ";
  }
}
