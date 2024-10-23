package _01_ArraryList;

import _00_Interface.List;

import java.util.Arrays;

public class ArrayList<E> implements List<E>, Cloneable{

    /**
     * 확장 가능한 용적의 한계값. "Java"에서 인덱스는 int 정수로 인덱싱
     * 이론적으로 Integer.MAX_VALUE(2^31 -1)의 인덱스를 갖을 수 있지만
     * VM에 따라 배열 크기 제한이 상이하며, 제한 값을 초과할 경우 다음과 같은 에러가 발생
     * "java.lang.OutOfMemoryError: Requested array size exceeds VM limit"
     * 위와 같은 안정성을 위해 이록적으로 가능한 최댓값에 8을 뺸 값으로 지정
     */

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private static final int DEFAULT_CAPACITY = 10; // 최소(기본) 용적 크기
    private static final Object[] EMPTY_ARRAY = {}; // 빈 배열

    private int size;
    Object[] array; // 요소를 담을 배열

    // 생성자1 (초기 공간 할당 X)
    public ArrayList(){
        this.array = EMPTY_ARRAY;
        this.size = 0;
    }

    // 생성자2 (초기 공간 할당 o)
    public ArrayList(int capacity){

        if(capacity > 0)
            this.array = new Object[capacity];
        else if (capacity ==0) {
            this.array = EMPTY_ARRAY;
        }else{
            throw new IllegalArgumentException("Illegal Capacity : " + capacity);
        }

        this.size = 0;
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(int index, E value) {

        if(index >size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(index == size){
            addLast(value);
        }
        else{
            for(int i  = size; i > index; i--){
                array[i] = array[i-1];
            }
            array[index] = value;
            size++;
        }
    }

    @Override
    public E remove(int index) {
        if(index >= size || index <0)
            throw new IndexOutOfBoundsException();

        // GC가 쓰지 않는 데이터라도 나중에 참조될 가능성이 있는 데이터로 볼 가능성이 높아짐으로
        // 메모리를 많이 잡아먹을 수 있는 가능성이 생김
        @SuppressWarnings("unchecked") E element = (E)array[index];
        array[index] = null;

        for (int i = index; i < size -1; i++) {
            array[i] = array[i + 1];
            array[i + 1] = null;
        }
        size--;
        resize();
        return element;
    }

    @Override
    public boolean remove(E value) {

        int index = indexOf(value);

        if(index == -1)
            return false;

        remove(index);
        return true;
    }

    // 붙히지 않을 경우 type safe(타입 안정성)에 대해 경고를 보낸다
    // ClassCaseException 이 뜨지 않으니 이 경고를 무시하겠다는 의미
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        if(index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        return (E)array[index];
    }

    @Override
    public void set(int index, E value) {
        if(index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        array[index] = value;
    }

    @Override
    public boolean contains(E value) {

        // indexOf의 값이 0이상이면 요소가 존재
        return indexOf(value) >= 0;
    }

    @Override
    public int indexOf(E value) {

        // "value"와 같은 객체(요소 값)일 경우 i(위치) 반환
        for (int i = 0; i < size; i++) {
            // 객체를 비교하기 떄문에 .equals() 로 비교
            // == 비교는 객체의 주소값을 비교하기 때문에 잘못된 결과를 초래
            if(array[i].equals(value)) return i;
        }
        // 일치하는 것이 없을 경우 -1을 반환
        return -1;
    }

    // 뒤에서부터 인덱스 찾기
    public int lastIndexOf(E value){
        for (int i = size -1; i > -1 ; i--) {
            if(array[i].equals(value)) return i;
        }

        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {

        for (int i = 0; i < size; i++) {
            array[i] = null;
        }

        size = 0;
        resize();
    }

    private void resize(){
        int array_capacity = array.length;

        // array capacity 가 0일 떄
        if(Arrays.equals(array, EMPTY_ARRAY)){
            array = new Object[DEFAULT_CAPACITY];
            return;
        }

        if(size == array_capacity){
            int new_capacity = array_capacity * 2;

            array = Arrays.copyOf(array, new_capacity);
            return;
        }

        if(size < (array_capacity / 2)){
            int new_capacity =  array_capacity / 2;

            array = Arrays.copyOf(array, Math.max(new_capacity, DEFAULT_CAPACITY));
        }
    }

    private void addLast(E value){

        if(size == array.length){
            resize();
        }

        array[size] = value;
        size++;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        try {
            ArrayList<?> cloneList = (ArrayList<?>) super.clone();

            cloneList.array = new Object[size];

            System.arraycopy(array, 0, cloneList.array, 0, size);

            return cloneList;
        }catch (CloneNotSupportedException e){
            throw new Error(e);
        }
    }

    public Object[] toArray(){
        return Arrays.copyOf(array, size);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a){
        if(a.length < size){
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }

        // a의 크기가 array보다 작을떄 a를 재할당하면서 array에 있던 모든 요소를 복사
        System.arraycopy(array, 0, a, 0, size);

        return a;
    }
}
