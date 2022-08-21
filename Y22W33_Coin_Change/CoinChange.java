package Y22W33_Coin_Change;

import java.util.Arrays;

import org.junit.Test;

public class CoinChange {
    @Test
    public void testSolveCoinChange(){
        assert 3 == solveCoinChange(new int[]{1, 2, 5},11);
        assert -1 == solveCoinChange(new int[]{2}, 3);
        assert 0 == solveCoinChange(new int[]{1}, 0);
    }

    public int solveCoinChange(int[] coins, int amount){
        // 假设兑换总额为 i 最少需要硬币 f(i) 个，对于某枚硬币：
        // * 不使用，f(i)
        // * 使用，f(i) = f(i - coins[i]) + 1
        // 则 f(i) = min(f(i), f(i - coins[i]) + 1)
        if(amount == 0){
            return 0;
        }
        int[] f = new int[amount+1]; 

        // 硬币都是整数，兑换 amount 不可能超过 amount+1个硬币
        Arrays.fill(f, amount + 1); 
        f[0] = 0;
        for (int i = 0; i <= amount; i++) {
            for(int coin: coins){
                if (i < coin){
                    continue;
                }
                f[i] = Math.min(f[i], f[i - coin] + 1);
            }
        }
        return f[amount] > amount ? -1: f[amount];
    }

    public int solveCoinChangeII(int[] coins, int amount){
        // II 求硬币的组合数，对某一枚硬币，可以用，也可以不用，即
        // f[i] = f[i] + f[i - coin]
        // f[0] = 1
        int[] f = new int[amount+1];
        for(int coin: coins){
            for(int i = coin; i <= amount; i++){
                f[i] = f[i] + f[i - coin];
            }
        }
        return f[amount];
    }
}
