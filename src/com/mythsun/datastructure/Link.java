package com.mythsun.datastructure;

/**
 * 1) 单链表反转
 * 反转每个结点前，记录next，等待操作完，把next作为下次的current
 * 2) 链表中环的检测
 * 快慢指针，如果有环，相对于慢指针，快指针每次前进1步，那么总会相遇。
 * 3) 两个有序的链表合并
 * 哨兵结点，目前没啥好说的，需要对比普通的实现来讲才行
 * 4) 删除链表倒数第n个结点
 * 滑动窗口思维，让快指针先走k，慢的再走，那么快指针到终点时，慢的刚好到要删除倒数第k个
 * 5) 求链表的中间结点
 * 快慢指针，快指针恒为慢指针的两倍，那么等快指针到底时，慢指针刚到中间
 * <p>
 * 除了检测环的方法，其余的都需要保证链表无环，不然直接死循环了
 */
public class Link {

    /**
     * 单链表反转
     *
     * @param list 要转换的link的头结点
     * @return 转换完的头结点
     */
    public static Node reverse(Node list) {
        // 赋值新变量，不直接操作传入对象
        Node current = list;
        // 返回的链表
        Node previous = null;
        while (current != null) {
            // 临时变量，趁当前结点没有改变指向时，先保存old的下一个结点
            Node next = current.next; // 局部内存
            // 当前结点先改变指向，从指向下一个改为指向前一个
            current.next = previous;
            // 上面一步已经完成了当前结点的反转，所以下面要为下一次循环做准备了
            // 对于下一次循环，当前结点相当于previous，所以赋值
            previous = current;
            // 对于下一次循环，上面保存的“下一个结点”就是current
            current = next;
        }
        return previous;
    }

    /**
     * 检测链表是否有环（快慢指针）
     *
     * @param list 头结点
     * @return
     */
    public static boolean checkCircle(Node list) {
        if (list == null) return false;
        // 快慢指针，所以两个变量是必须的，其实这俩变量要么相邻要么相等就好，中间不要有“第三者”即可，否则“第三者”们刚好是环，就出问题了
        Node fast = list.next;
        Node slow = list;

        while (fast != null && fast.next != null) {
            // 快指针一次走两步
            fast = fast.next.next;
            // 慢指针一次走一步
            slow = slow.next;
            // 两个指针相遇
            if (slow == fast) return true;
        }

        return false;
    }

    /**
     * 有序链表合并，注意是有序，无序的话，直接放最后就得了
     * 传入的是两个链表的头指针
     * 返回合并后的头指针
     */
    public Node mergeTwoLists(Node l1, Node l2) {
        // 哨兵，不存储真实数据。利用哨兵结点简化实现难度
        Node soldier = new Node(0); //利用哨兵结点简化实现难度 技巧三
        Node foreverHead = soldier;

        while (l1 != null && l2 != null) {
            // 两个链谁小谁跟在“哨兵”后面
            if (l1.data < l2.data) {
                foreverHead.next = l1;
                l1 = l1.next;
            } else {
                foreverHead.next = l2;
                l2 = l2.next;
            }
            // “哨兵”更新为刚刚小的那个的下一个，继续下一个循环
            foreverHead = foreverHead.next;
        }
        // 因为上面判断的是  l1 != null && l2 != null  ，那么就有可能有个短链走完了，链还没走完，这时只需要把后面的都“指向”就好了
        if (l1 != null) {
            foreverHead.next = l1;
        }
        if (l2 != null) {
            foreverHead.next = l2;
        }
        // soldier对象始终指向的是new Node(0)，也就是真正的head，我们上面操作的都是foreverHead，所以这里这样就能返回合并后的head了
        return soldier.next;
    }

    /**
     * 删除倒数第K个结点
     *
     * @param list 链表头结点
     * @param k    要大于0
     * @return 链表头结点
     */
    public static Node deleteLastKth(Node list, int k) {
        Node fast = list;
        int i = 1;
        // 检查第k个结点是否存在
        while (fast != null && i < k) {
            fast = fast.next;
            ++i;    // 这里i++和++i没有区别
        }

        if (fast == null) return list;
        // 快慢指针的慢指针，快指针复用上面的fast
        Node slow = list;
        // 临时变量
        Node prev = null;
        while (fast.next != null) {
            // 上面检查的时候，快指针已经提前走了k了，此时慢指针才开始走，所以当快指针到头的时候，慢指针刚到到删除的位置
            fast = fast.next;
            // 记录慢指针每次循环前的“上一个指针”，用于指向删除后的新的结点
            prev = slow;
            slow = slow.next;
        }
        // 快指针已经指向最后一个，也就是k相当于删除的是第一个结点，比如链表长度为5，此时k=5，所以直接next就算删除完了
        if (prev == null) {
            list = list.next;
        } else {
            // 记录的“上一个结点”此时直接执行下下个结点，就算删除了
            prev.next = prev.next.next;
        }
        return list;
    }

    /**
     * 求中间结点
     *
     * @param list
     * @return
     */
    public static Node findMiddleNode(Node list) {
        if (list == null) return null;

        Node fast = list;
        Node slow = list;
        // 快慢结点，因为快的恒为慢的两倍，所以当快的指向null的时候，慢的刚好到中间
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    /**
     * 打印全部结点
     *
     * @param list
     */
    public static void printAll(Node list) {
        Node p = list;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    public static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }
    }

}
