package assignment2.retrogames;

public class OrderedDictionary implements OrderedDictionaryADT {

    Node root;

    OrderedDictionary() {
        root = new Node();
    }

    /**
     * Returns the Record object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/retrogames/DictionaryException.java
     */
    @Override
    public RetroGameRecord find(DataKey k) throws DictionaryException {
        Node current = root;
        int comparison;
        if (root.isEmpty()) {
            throw new DictionaryException("There is no record matches the given key");
        }

        while (true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison == 0) { // key found
                return current.getData();
            }
            if (comparison == 1) {
                if (current.getLeftChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getLeftChild();
            } else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getRightChild();
            }
        }

    }

    /**
     * Inserts r into the ordered dictionary. It throws a DictionaryException if
     * a record with the same key as r is already in the dictionary.
     *
     * @param r
     * @throws games.DictionaryException
     */
    @Override
    public void insert(RetroGameRecord r) throws DictionaryException {
        if(root.isEmpty()) {
            root.setData(r);
            return;
        }
        // Completed?
        Node currentNode = root;
        Node nodeBefore = null;
        while((currentNode != null)) {
            nodeBefore = currentNode;
            currentNode = currentNode.getRightChild();
        }

        Node newNode = new Node(r);
        if(nodeBefore == null) {
            newNode.setRightChild(root);
            root = newNode;
        }
        else {
            newNode.setRightChild(currentNode);
            newNode.setLeftChild(nodeBefore);
            nodeBefore.setRightChild(newNode);
        }
    }

    /**
     * Removes the record with Key k from the dictionary. It throws a
     * DictionaryException if the record is not in the dictionary.
     *
     * @param k
     * @throws RetroGames.DictionaryException
     */
    @Override
    public void remove(DataKey k) throws DictionaryException {
        // Write this method
        if(root.isEmpty())
            return;

        Node currentNode = root;
        Node delete = root;
        while((currentNode != null)) {
            if (k.compareTo(currentNode.getData().getDataKey()) == 0) {
                delete = currentNode;
            }
            currentNode = currentNode.getRightChild();
        }

        if(delete != null) {
            if(delete == root && root.hasRightChild())
            {
                Node temp = root.getRightChild();
                root.setRightChild(null);
                root = temp;
            }
            else if(delete.hasLeftChild() && delete.hasRightChild())
            {
                Node temp = delete.getRightChild();
                Node temp2 = delete.getLeftChild();
                temp.setLeftChild(delete.getLeftChild());
                temp2.setRightChild(delete.getRightChild());
                delete.setRightChild(null);
                delete.setLeftChild(null);
            }
            else if(delete.hasLeftChild() && !(delete.hasRightChild()))
            {
                Node temp = delete.getLeftChild();
                delete.setLeftChild(null);
                temp.setRightChild(null);
            }
            else
            {
                root = new Node();
            }
        }
    }

    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws RetroGames.DictionaryException
     */
    @Override
    public RetroGameRecord successor(DataKey k) throws DictionaryException{
        if(root.isEmpty())
            return null;

        if(k.compareTo(smallest().getDataKey()) == 0)
            return smallest();

        Node currentNode = root;
        Node temp = root;
        while((currentNode != null)) {
            if (k.compareTo(currentNode.getData().getDataKey()) == 0) {
                temp = currentNode;
            }
            currentNode = currentNode.getRightChild();
        }
        RetroGameRecord recordToReturn = null;
        assert temp != null;
        if(temp.hasRightChild())
            recordToReturn = temp.getRightChild().getData();

        return recordToReturn;
    }


    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * largest key smaller than k; it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws RetroGames.DictionaryException
     */
    @Override
    public RetroGameRecord predecessor(DataKey k) throws DictionaryException{
        if(root.isEmpty())
            return null;

        if(k.compareTo(root.getData().getDataKey()) == 0)
            return root.getData();

        Node currentNode = root;
        Node temp = root;
        while((currentNode != null)) {
            if (k.compareTo(currentNode.getData().getDataKey()) == 0) {
                temp = currentNode;
            }
            currentNode = currentNode.getRightChild();
        }
        RetroGameRecord recordToReturn = null;
        assert temp != null;
        if(temp.hasLeftChild())
            recordToReturn = temp.getLeftChild().getData();

        return recordToReturn;
    }

    /**
     * Returns the record with smallest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     *
     * @return
     */
    @Override
    public RetroGameRecord smallest() throws DictionaryException{
        // Write this method
        if(root.isEmpty())
            return null;

        Node currentNode = root;
        Node smallest = root;
        DataKey key = root.getData().getDataKey();
        while((currentNode != null)) {
            if(key.compareTo(currentNode.getData().getDataKey()) == -1) {
                key = currentNode.getData().getDataKey();
                smallest = currentNode;
            }

            currentNode = currentNode.getRightChild();
        }
        RetroGameRecord recordToReturn = smallest.getData();

        return recordToReturn;
    }

    /*
     * Returns the record with largest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     */
    @Override
    public RetroGameRecord largest() throws DictionaryException{
        if(root.isEmpty())
            return null;

        Node currentNode = root;
        Node largest = root;
        DataKey key = root.getData().getDataKey();
        while((currentNode != null)) {
            if(key.compareTo(currentNode.getData().getDataKey()) == 1) {
                key = currentNode.getData().getDataKey();
                largest = currentNode;
            }

            currentNode = currentNode.getRightChild();
        }
        RetroGameRecord recordToReturn = largest.getData();

        return recordToReturn;
    }

    /* Returns true if the dictionary is empty, and true otherwise. */
    @Override
    public boolean isEmpty (){
        return root.isEmpty();
    }
}
