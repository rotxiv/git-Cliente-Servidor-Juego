package Negocio.Cliente;

import java.util.*;

public class Game {

    protected static int[] state = new int[]{0, 0, 0, 0, 0}; //disponible para lanzar = 0 ; no disponible para lanzar = 1;
    protected static Integer[] level = new Integer[]{0, 0, 0, 0, 0}; //en que tiro se saco el valor del dado

    private Integer[] dice = new Integer[5];
    private int[] finalValueTable = new int[]{0,0,0,0,0,0,0,0,0,0};
    private String category;

    private int cant;

    public Game() {
        this.cant = 1;
    }
    public Integer[] getDice() {
        return dice;
    }
    public int[] getState() {
        return state;
    }

    public static Integer[] getLevel() {
        return level;
    }

    public String[] getFinalValueTable() {
        return Arrays.stream(finalValueTable).mapToObj(String::valueOf).toArray(String[]::new);
    }
    public int getCant() {
        return cant;
    }
    public String getCategory() {
        return category;
    }
    public void setState(int[] state) {
        Game.state = state;
    }
    public void setCant(int cant) {
        this.cant = cant;
    }
    public void resetState() {
        Arrays.fill(state, 0);
    }
    public void resetLevel() {
        Arrays.fill(state, 0);
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setStateDice(int pos, int stateDice) {
        state[pos] = stateDice;
    }
    public void setLevelDice(int pos, int levelDice) {
        level[pos] = levelDice;
    }
    public void rollDice() {
        Random random = new Random();
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0)
                dice[i] = random.nextInt(6) + 1;
        }
    }
    public void processGame() {
        switch (category) {
            case "Uno" -> processOneCategory();
            case "Dos" -> processTwoCategory();
            case "Tres" -> processThreeCategory();
            case "Cuatro" -> processFourCategory();
            case "Cinco" -> processFiveCategory();
            case "Seis" -> processSixCategory();
            case "Escalera" -> processLadderCategory();
            case "Full" -> processFullCategory();
            case "Poker" -> processPokerCategory();
            case "Grand" -> processGrandCategory();
        }
    }
    private void processGrandCategory() {
        Integer val = dice[1];
        finalValueTable[9] = 100;
        for (Integer d : dice)
            if (!Objects.equals(d, val)) {
                finalValueTable[9] = 0;
                System.out.println("el valor" + d + " es distinto de " + val);
                break;
            }
    }
    private void processPokerCategory() {
        HashMap<Integer,Integer> map = new HashMap<>();
        List<Integer> array = Arrays.asList(level);
        System.out.println(array);
        for (int aux : dice) {
            if (map.containsKey(aux))
                map.put(aux, map.get(aux) + 1);
            else
                map.put(aux, 1);
        }
        boolean aux = false;
        for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
            if (entry.getValue() == 4) {
                aux = true;
                break;
            }
        }
        if (aux) {
            if (array.contains(2) | array.contains(3))
                finalValueTable[7] = 40;
            else if (array.contains(1))
                finalValueTable[7] = 45;
        }
    }
    private void processFullCategory() {
        HashMap<Integer,Integer> map = new HashMap<>();
        List<Integer> array = Arrays.asList(level);
        for (int aux : dice) {
            if (map.containsKey(aux))
                map.put(aux, map.get(aux) + 1);
            else
                map.put(aux, 1);
        }
        boolean aux = false;    boolean aux2 = false;
        for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
            if (entry.getValue() == 3) aux = true;
            if (entry.getValue() == 2) aux2 = true;
        }
        if (aux && aux2) {
            if (array.contains(2) | array.contains(3))
                finalValueTable[4] = 30;
            else if (array.contains(1))
                finalValueTable[4] = 35;
        }
    }
    private void processLadderCategory() {
        Integer[] optionOne = new Integer[]{1,2,3,4,5};
        Integer[] optionTwo = new Integer[]{2,3,4,5,6};
        Integer[] optionThree = new Integer[]{3,4,5,6,1};
        for (Integer[] integers : Arrays.asList(optionOne, optionTwo, optionThree)) {
            if (Arrays.equals(integers, dice)) {
                if (Arrays.asList(level).contains(3))
                    finalValueTable[1] = 20;
                else if (Arrays.asList(level).contains(2))
                    finalValueTable[1] = 20;
                else if (Arrays.asList(level).contains(1))
                    finalValueTable[1] = 25;
            }
        }
    }
    private void processSixCategory() {
        for (int d : dice) {
            if (d == 6)
                finalValueTable[8] = finalValueTable[8] + 6;
        }
    }
    private void processFiveCategory() {
        for (int d : dice) {
            if (d == 5)
                finalValueTable[5] = finalValueTable[5] + 5;
        }
    }
    private void processFourCategory() {
        for (int d : dice) {
            if (d == 4)
                finalValueTable[2] = finalValueTable[2] + 4;
        }
    }
    private void processThreeCategory() {
        for (int d : dice) {
            if (d == 3)
                finalValueTable[6] = finalValueTable[6] + 3;
        }
    }
    private void processTwoCategory() {
        for (int d : dice) {
            if (d == 2)
                finalValueTable[3] = finalValueTable[3] + 2;
        }
    }
    private void processOneCategory() {
        for (int d : dice) {
            if (d == 1)
                finalValueTable[0] = finalValueTable[0] + 1;
        }
    }
    public void demoGrand() {
        Arrays.fill(dice, 5);
        Arrays.fill(level, 1);
        Arrays.fill(state,0);
    }
}
