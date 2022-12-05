import java.util.ArrayList;

public class AllSort {

    public static <E> ArrayList<ArrayList<E>> getSort(ArrayList<E> list) {
        boolean[] used = new boolean[list.size()];
        ArrayList<ArrayList<E>> resultArrayList = new ArrayList<>();
        backtrack(list, new ArrayList<>(), used, resultArrayList);
        return resultArrayList;
    }

    private static <E> void backtrack(ArrayList<E> nums, ArrayList<E> curr, boolean[] used, ArrayList<ArrayList<E>> resultArrayList) {
        if (curr.size() == nums.size()) {
            //如果有特殊需要，比如需要处理每一次排列好的值，那么可以在这里处理，curr是个ArrayList
            resultArrayList.add(new ArrayList<>(curr));
            return;
        }
        for (int i = 0; i < nums.size(); i++) {
            if (used[i]) continue;
            curr.add(nums.get(i));
            used[i] = true;
            backtrack(nums, curr, used, resultArrayList);
            curr.remove(curr.size() - 1);
            used[i] = false;
        }
    }
}
