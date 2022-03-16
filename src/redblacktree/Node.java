package redblacktree;

public final class Node<K extends Comparable<? super K>, V> {

  private Node<K, V> left;
  private Node<K, V> right;
  private Node<K, V> parent;

  private final K key;
  private V value;
  private Color color;

  public Node(K key, V value, Color color) {
    if (key == null) {
      throw new IllegalArgumentException("Key cannot be null!");
    }

    this.key = key;
    this.value = value;
    this.color = color;
  }

  /* Tree operations */

  public Node<K, V> getParent() {
    return this.parent;
  }

  private void setParent(Node<K, V> node) {
    this.parent = node;
  }

  public void setRight(Node<K, V> node) {
    // Get rid of current right child if exists
    if (right != null && right.getParent() == this) {
      right.setParent(null);
    }
    // Set new right child to node
    this.right = node;
    if (node != null) {
      node.setParent(this);
    }
  }

  public void setLeft(Node<K, V> node) {
    if (left != null && left.getParent() == this) {
      left.setParent(null);
    }
    this.left = node;
    if (node != null) {
      node.setParent(this);
    }
  }

  public boolean isLeftChild() {
    return parent != null && parent.getLeft() == this;
  }

  public boolean isRightChild() {
    return parent != null && parent.getRight() == this;
  }

  public Node<K, V> getLeft() {
    return this.left;
  }

  public Node<K, V> getRight() {
    return this.right;
  }

  public Node<K, V> getGrandparent() {
    return this.parent != null ? this.parent.parent : null;
  }

  public Node<K, V> getUncle() {
    Node<K, V> grandparent = getGrandparent();
    return grandparent != null
        ? (grandparent.left == this.parent ? grandparent.right : grandparent.left)
        : null;
  }

  public Node<K, V> rotateRight() {
    Node<K, V> tempTree = getLeft();
    setLeft(tempTree.right);
    reparent(tempTree);
    tempTree.setRight(this);
    return tempTree;
  }

  public Node<K, V> rotateLeft() {
    Node<K, V> tempTree = getRight();
    setRight(tempTree.left);
    reparent(tempTree);
    tempTree.setLeft(this);
    return tempTree;
  }

  private void reparent(Node<K, V> newChild) {
    if (getParent() != null) {
      if (isLeftChild()) {
        getParent().setLeft(newChild);
      } else {
        getParent().setRight(newChild);
      }
    }
  }

  /* Colour operations */

  public boolean isBlack() {
    return color == Color.BLACK;
  }

  public boolean isRed() {
    return color == Color.RED;
  }

  public void setRed() {
    color = Color.RED;
  }

  public void setBlack() {
    color = Color.BLACK;
  }

  /* Key value operations */

  public K getKey() {
    return key;
  }

  public V getValue() {
    return value;
  }

  public void setValue(V value) {
    this.value = value;
  }

  public String toString() {
    return "{ " + color + ": " + left + " [" + key + ", " + value + "] " + right + " }";
  }
}
