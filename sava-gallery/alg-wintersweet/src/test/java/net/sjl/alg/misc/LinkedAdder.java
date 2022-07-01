package net.sjl.alg.misc;

public class LinkedAdder {

	public class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

		ListNode head1 = l1, head2 = l2;
		ListNode head = null, tail = null;
		while (head1 != null || head2 != null) {
			if (head == null) {
				head = new ListNode(0);
				tail = head;
			} else {

				tail.next = new ListNode(tail.val > 9 ? 1 : 0);
				tail.val = tail.val > 9 ? tail.val - 10 : tail.val;
				tail = tail.next;
			}

			if (head1 == null) {
				tail.val += head2.val;
				head2 = head2.next;
			} else if (head2 == null) {
				tail.val += head1.val;
				head1 = head1.next;
			} else {
				tail.val += head1.val + head2.val;
				head1 = head1.next;
				head2 = head2.next;
			}
		}

		if (tail != null && tail.val > 9) {
			tail.next = new ListNode(tail.val > 9 ? 1 : 0);
			tail.val = tail.val - 10;
			tail = tail.next;
		}

		return head;

	}

}
