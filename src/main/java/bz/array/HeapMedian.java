package bz.array;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 数据流中的中位数：基于大小堆算法
 * 实现插入时间复杂度O(logn)，获取时间复杂度O(1)，优于数组，链表，二叉搜索树等
 */
public class HeapMedian {
    // 中位数的右边维护最小堆
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    // 中位数的左边维护最大堆
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(((o1, o2) -> o2 - o1));

    public void insert(int[] dataArr) {
        Arrays.stream(dataArr).forEach(data -> insert(data));
    }
    public void insert(int data) {
        // 为了保持大小堆的数量均衡：如果数据流总数为偶数，则插入最小堆。
        if (((minHeap.size() + maxHeap.size() & 1) == 0)) {
            // 如果待插入数 比最大堆还小，则需要置换最大堆中的最大的数到 最小堆
            if (maxHeap.size() > 0 && data < maxHeap.peek()) {
                maxHeap.offer(data);
                data = maxHeap.poll();
            }
            minHeap.offer(data);
        } else {
            // 为了保持大小堆的数量均衡：如果数据流总数为奇数，则插入最大堆。
            if (minHeap.size() > 0 && data > minHeap.peek()) {
                minHeap.offer(data);
                data = minHeap.poll();
            }
            maxHeap.offer(data);
        }
    }

    public int getMedian() {
        int size = maxHeap.size() + minHeap.size();
        if (size == 0) {
            throw new IllegalStateException("Err");
        }
        // 奇数个数是在偶数基础上插入后才变成奇数个数的， 因此最小堆的数量多1
        return (size & 1) == 1 ? minHeap.peek() : (maxHeap.peek() + minHeap.peek()) / 2;
    }
}
