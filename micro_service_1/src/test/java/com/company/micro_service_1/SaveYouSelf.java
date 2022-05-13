package com.company.micro_service_1;

/**
 * @Classname SaveYouSelf
 * @Description 自救
 * @Version 1.0.0
 * @Date 20/4/2022 下午 8:28
 * @Created by 李文博
 */
public class SaveYouSelf {
    private static SaveYouSelf SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("Yes I still alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed");
        SaveYouSelf.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new SaveYouSelf();
        // 引用置空
        SAVE_HOOK = null;
        System.gc();
        // 暂停 0.5s
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("No I am dead");
        }
        // finalize方法只会执行一次

        SAVE_HOOK = null;
        System.gc();
        // 暂停 0.5s
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("No I am dead");
        }
    }
}
