package com.houarizegai.calculator.view;

import com.houarizegai.calculator.data.Theme;
import com.houarizegai.calculator.service.ThemeLoader;
import com.houarizegai.calculator.view.title.Buttons;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.Map;
import java.util.regex.Pattern;

import static com.houarizegai.calculator.service.ColorConverter.hex2Color;

public class CalculatorUI {

    private static final String FONT_NAME = "Comic Sans MS";
    private static final String DOUBLE_OR_NUMBER_REGEX = "(-?\\d+[.]\\d*)|(\\d+)|(-\\d+)";
    private static final String APPLICATION_TITLE = "Calculator";
    private static final String INITIAL_VALUE = "0";
    private static final int WINDOW_WIDTH = 410;
    private static final int WINDOW_HEIGHT = 600;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 70;
    private static final int MARGIN_X = 20;
    private static final int MARGIN_Y = 60;

    private final JFrame window;
    private JComboBox<String> comboCalculatorType;
    private JComboBox<String> comboTheme;
    private JTextField inputScreen;
    private JButton btnC;
    private JButton btnBack;
    private JButton btnMod;
    private JButton btnDiv;
    private JButton btnMul;
    private JButton btnSub;
    private JButton btnAdd;
    private JButton btn0;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9;
    private JButton btnPoint;
    private JButton btnEqual;
    private JButton btnRoot;
    private JButton btnPower;
    private JButton btnLog;

    private Buttons selectedOperator = Buttons.NOTHING;
    private boolean shouldProceed = true; // For calculate with Opt != (=)
    private boolean shouldAddToDisplay = true; // Connect numbers in display
    private double typedValue = 0;

    private final Map<String, Theme> themesMap;

    public CalculatorUI() {
        themesMap = ThemeLoader.loadThemes();

        window = new JFrame(APPLICATION_TITLE);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setLocationRelativeTo(null);

        int[] columns = {MARGIN_X, MARGIN_X + 90, MARGIN_X + 90 * 2, MARGIN_X + 90 * 3, MARGIN_X + 90 * 4};
        int[] rows = {MARGIN_Y, MARGIN_Y + 100, MARGIN_Y + 100 + 80, MARGIN_Y + 100 + 80 * 2, MARGIN_Y + 100 + 80 * 3, MARGIN_Y + 100 + 80 * 4};

        initInputScreen(columns, rows);
        initButtons(columns, rows);
        initCalculatorTypeSelector();

        initThemeSelector();

        window.setLayout(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public double calculate(double firstNumber, double secondNumber, Buttons operator) {
        switch (operator) {
            case ADD:
                return firstNumber + secondNumber;
            case MINUS:
                return firstNumber - secondNumber;
            case MULT:
                return firstNumber * secondNumber;
            case DIV:
                return firstNumber / secondNumber;
            case PERCENT:
                return firstNumber % secondNumber;
            case POW:
                return Math.pow(firstNumber, secondNumber);
            default:
                return secondNumber;
        }
    }

    private void initThemeSelector() {
        comboTheme = createComboBox(themesMap.keySet().toArray(new String[0]), 230, 30, "Theme");
        comboTheme.addItemListener(event -> {
            if (event.getStateChange() != ItemEvent.SELECTED)
                return;

            String selectedTheme = (String) event.getItem();
            applyTheme(themesMap.get(selectedTheme));
        });

        if (themesMap.entrySet().iterator().hasNext()) {
            applyTheme(themesMap.entrySet().iterator().next().getValue());
        }
    }

    private void initInputScreen(int[] columns, int[] rows) {
        inputScreen = new JTextField(INITIAL_VALUE);
        inputScreen.setBounds(columns[0], rows[0], 350, 70);
        inputScreen.setEditable(false);
        inputScreen.setBackground(Color.WHITE);
        inputScreen.setFont(new Font(FONT_NAME, Font.PLAIN, 33));
        window.add(inputScreen);
    }

    private void initCalculatorTypeSelector() {
        comboCalculatorType = createComboBox(new String[]{"Standard", "Scientific"}, 20, 30, "Calculator type");
        comboCalculatorType.addItemListener(event -> {
            if (event.getStateChange() != ItemEvent.SELECTED)
                return;

            String selectedItem = (String) event.getItem();
            switch (selectedItem) {
                case "Standard":
                    window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                    btnRoot.setVisible(false);
                    btnPower.setVisible(false);
                    btnLog.setVisible(false);
                    break;
                case "Scientific":
                    window.setSize(WINDOW_WIDTH + 80, WINDOW_HEIGHT);
                    btnRoot.setVisible(true);
                    btnPower.setVisible(true);
                    btnLog.setVisible(true);
                    break;
            }
        });
    }

    private void initButtons(int[] columns, int[] rows) {
        btnC = createButton(Buttons.RESET.title, columns[0], rows[1]);
        btnC.addActionListener(event -> {
            inputScreen.setText(INITIAL_VALUE);
            selectedOperator = Buttons.NOTHING;
            typedValue = 0;
        });

        btnBack = createButton(Buttons.BACKSPACE.title, columns[1], rows[1]);
        btnBack.addActionListener(event -> {
            String str = inputScreen.getText();
            StringBuilder str2 = new StringBuilder();
            for (int i = 0; i < (str.length() - 1); i++) {
                str2.append(str.charAt(i));
            }
            if (str2.toString().equals("")) {
                inputScreen.setText(INITIAL_VALUE);
            } else {
                inputScreen.setText(str2.toString());
            }
        });

        btnMod = createButton(Buttons.PERCENT.title, columns[2], rows[1]);
        btnMod.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()) || !shouldProceed)
                return;

            typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
            if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                inputScreen.setText(String.valueOf((int) typedValue));
            } else {
                inputScreen.setText(String.valueOf(typedValue));
            }
            selectedOperator = Buttons.PERCENT;
            shouldProceed = false;
            shouldAddToDisplay = false;
        });

        btnDiv = createButton(Buttons.DIV.title, columns[3], rows[1]);
        btnDiv.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (shouldProceed) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = Buttons.DIV;
                shouldProceed = false;
                shouldAddToDisplay = false;
            } else {
                selectedOperator = Buttons.NOTHING;
            }
        });

        btn7 = createButton(Buttons.SEVEN.title, columns[0], rows[2]);
        btn7.addActionListener(event -> {
            if (shouldAddToDisplay) {
                if (Pattern.matches("0*", inputScreen.getText())) {
                    inputScreen.setText(Buttons.SEVEN.title);
                } else {
                    inputScreen.setText(inputScreen.getText() + Buttons.SEVEN.title);
                }
            } else {
                inputScreen.setText(Buttons.SEVEN.title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });

        btn8 = createButton(Buttons.EIGHT.title, columns[1], rows[2]);
        btn8.addActionListener(event -> {
            if (shouldAddToDisplay) {
                if (Pattern.matches("0*", inputScreen.getText())) {
                    inputScreen.setText(Buttons.EIGHT.title);
                } else {
                    inputScreen.setText(inputScreen.getText() + Buttons.EIGHT.title);
                }
            } else {
                inputScreen.setText(Buttons.EIGHT.title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });

        btn9 = createButton(Buttons.NINE.title, columns[2], rows[2]);
        btn9.addActionListener(event -> {
            if (shouldAddToDisplay) {
                if (Pattern.matches("0*", inputScreen.getText())) {
                    inputScreen.setText(Buttons.NINE.title);
                } else {
                    inputScreen.setText(inputScreen.getText() + Buttons.NINE.title);
                }
            } else {
                inputScreen.setText(Buttons.NINE.title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });

        btnMul = createButton(Buttons.MULT.title, columns[3], rows[2]);
        btnMul.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (shouldProceed) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = Buttons.MULT;
                shouldProceed = false;
                shouldAddToDisplay = false;
            } else {
                selectedOperator = Buttons.MULT;
            }
        });

        btn4 = createButton(Buttons.FOUR.title, columns[0], rows[3]);
        btn4.addActionListener(event -> {
            if (shouldAddToDisplay) {
                if (Pattern.matches("0*", inputScreen.getText())) {
                    inputScreen.setText(Buttons.FOUR.title);
                } else {
                    inputScreen.setText(inputScreen.getText() + Buttons.FOUR.title);
                }
            } else {
                inputScreen.setText(Buttons.FOUR.title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });

        btn5 = createButton(Buttons.FIVE.title, columns[1], rows[3]);
        btn5.addActionListener(event -> {
            if (shouldAddToDisplay) {
                if (Pattern.matches("0*", inputScreen.getText())) {
                    inputScreen.setText(Buttons.FIVE.title);
                } else {
                    inputScreen.setText(inputScreen.getText() + Buttons.FIVE.title);
                }
            } else {
                inputScreen.setText(Buttons.FIVE.title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });

        btn6 = createButton(Buttons.SIX.title, columns[2], rows[3]);
        btn6.addActionListener(event -> {
            if (shouldAddToDisplay) {
                if (Pattern.matches("0*", inputScreen.getText())) {
                    inputScreen.setText(Buttons.SIX.title);
                } else {
                    inputScreen.setText(inputScreen.getText() + Buttons.SIX.title);
                }
            } else {
                inputScreen.setText(Buttons.SIX.title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });

        btnSub = createButton(Buttons.MINUS.title, columns[3], rows[3]);
        btnSub.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (shouldProceed) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }

                selectedOperator = Buttons.MINUS;
                shouldProceed = false;
                shouldAddToDisplay = false;
            } else {
                selectedOperator = Buttons.MINUS;
            }
        });

        btn1 = createButton(Buttons.ONE.title, columns[0], rows[4]);
        btn1.addActionListener(event -> {
            if (shouldAddToDisplay) {
                if (Pattern.matches("0*", inputScreen.getText())) {
                    inputScreen.setText(Buttons.ONE.title);
                } else {
                    inputScreen.setText(inputScreen.getText() + Buttons.ONE.title);
                }
            } else {
                inputScreen.setText(Buttons.ONE.title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });

        btn2 = createButton(Buttons.TWO.title, columns[1], rows[4]);
        btn2.addActionListener(event -> {
            if (shouldAddToDisplay) {
                if (Pattern.matches("0*", inputScreen.getText())) {
                    inputScreen.setText(Buttons.TWO.title);
                } else {
                    inputScreen.setText(inputScreen.getText() + Buttons.TWO.title);
                }
            } else {
                inputScreen.setText(Buttons.TWO.title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });

        btn3 = createButton(Buttons.THREE.title, columns[2], rows[4]);
        btn3.addActionListener(event -> {
            if (shouldAddToDisplay) {
                if (Pattern.matches("0*", inputScreen.getText())) {
                    inputScreen.setText(Buttons.THREE.title);
                } else {
                    inputScreen.setText(inputScreen.getText() + Buttons.THREE.title);
                }
            } else {
                inputScreen.setText(Buttons.THREE.title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });

        btnAdd = createButton(Buttons.ADD.title, columns[3], rows[4]);
        btnAdd.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (shouldProceed) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = Buttons.ADD;
                shouldProceed = false;
                shouldAddToDisplay = false;
            } else {
                selectedOperator = Buttons.ADD;
            }
        });

        btnPoint = createButton(Buttons.DOT.title, columns[0], rows[5]);
        btnPoint.addActionListener(event -> {
            if (shouldAddToDisplay) {
                if (!inputScreen.getText().contains(Buttons.DOT.title)) {
                    inputScreen.setText(inputScreen.getText() + Buttons.DOT.title);
                }
            } else {
                inputScreen.setText(INITIAL_VALUE + Buttons.DOT.title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });

        btn0 = createButton(Buttons.ZERO.title, columns[1], rows[5]);
        btn0.addActionListener(event -> {
            if (shouldAddToDisplay) {
                if (Pattern.matches("0*", inputScreen.getText())) {
                    inputScreen.setText(Buttons.ZERO.title);
                } else {
                    inputScreen.setText(inputScreen.getText() + Buttons.ZERO.title);
                }
            } else {
                inputScreen.setText(Buttons.ZERO.title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });

        btnEqual = createButton(Buttons.EQUAL.title, columns[2], rows[5]);
        btnEqual.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (shouldProceed) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = Buttons.EQUAL;
                shouldAddToDisplay = false;
            }
        });
        btnEqual.setSize(2 * BUTTON_WIDTH + 10, BUTTON_HEIGHT);

        btnRoot = createButton(Buttons.SQRT.title, columns[4], rows[1]);
        btnRoot.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (shouldProceed) {
                typedValue = Math.sqrt(Double.parseDouble(inputScreen.getText()));
                if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = Buttons.SQRT;
                shouldAddToDisplay = false;
            }
        });
        btnRoot.setVisible(false);

        btnPower = createButton(Buttons.POW.title, columns[4], rows[2]);
        btnPower.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (shouldProceed) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = Buttons.POW;
                shouldProceed = false;
                shouldAddToDisplay = false;
            } else {
                selectedOperator = Buttons.POW;
            }
        });
        btnPower.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        btnPower.setVisible(false);

        btnLog = createButton(Buttons.LN.title, columns[4], rows[3]);
        btnLog.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (shouldProceed) {
                typedValue = Math.log(Double.parseDouble(inputScreen.getText()));
                if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = Buttons.LN;
                shouldAddToDisplay = false;
            }
        });
        btnLog.setVisible(false);
    }

    private JComboBox<String> createComboBox(String[] items, int x, int y, String toolTip) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setBounds(x, y, 140, 25);
        combo.setToolTipText(toolTip);
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        window.add(combo);

        return combo;
    }

    private JButton createButton(String label, int x, int y) {
        JButton btn = new JButton(label);
        btn.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 28));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusable(false);
        window.add(btn);

        return btn;
    }

    private void applyTheme(Theme theme) {
        window.getContentPane().setBackground(hex2Color(theme.getApplicationBackground()));

        comboCalculatorType.setForeground(hex2Color(theme.getTextColor()));
        comboTheme.setForeground(hex2Color(theme.getTextColor()));
        inputScreen.setForeground(hex2Color(theme.getTextColor()));
        btn0.setForeground(hex2Color(theme.getTextColor()));
        btn1.setForeground(hex2Color(theme.getTextColor()));
        btn2.setForeground(hex2Color(theme.getTextColor()));
        btn3.setForeground(hex2Color(theme.getTextColor()));
        btn4.setForeground(hex2Color(theme.getTextColor()));
        btn5.setForeground(hex2Color(theme.getTextColor()));
        btn6.setForeground(hex2Color(theme.getTextColor()));
        btn7.setForeground(hex2Color(theme.getTextColor()));
        btn8.setForeground(hex2Color(theme.getTextColor()));
        btn9.setForeground(hex2Color(theme.getTextColor()));
        btnPoint.setForeground(hex2Color(theme.getTextColor()));
        btnC.setForeground(hex2Color(theme.getTextColor()));
        btnBack.setForeground(hex2Color(theme.getTextColor()));
        btnMod.setForeground(hex2Color(theme.getTextColor()));
        btnDiv.setForeground(hex2Color(theme.getTextColor()));
        btnMul.setForeground(hex2Color(theme.getTextColor()));
        btnSub.setForeground(hex2Color(theme.getTextColor()));
        btnAdd.setForeground(hex2Color(theme.getTextColor()));
        btnRoot.setForeground(hex2Color(theme.getTextColor()));
        btnLog.setForeground(hex2Color(theme.getTextColor()));
        btnPower.setForeground(hex2Color(theme.getTextColor()));
        btnEqual.setForeground(hex2Color(theme.getBtnEqualTextColor()));

        comboCalculatorType.setBackground(hex2Color(theme.getApplicationBackground()));
        comboTheme.setBackground(hex2Color(theme.getApplicationBackground()));
        inputScreen.setBackground(hex2Color(theme.getApplicationBackground()));
        btn0.setBackground(hex2Color(theme.getNumbersBackground()));
        btn1.setBackground(hex2Color(theme.getNumbersBackground()));
        btn2.setBackground(hex2Color(theme.getNumbersBackground()));
        btn3.setBackground(hex2Color(theme.getNumbersBackground()));
        btn4.setBackground(hex2Color(theme.getNumbersBackground()));
        btn5.setBackground(hex2Color(theme.getNumbersBackground()));
        btn6.setBackground(hex2Color(theme.getNumbersBackground()));
        btn7.setBackground(hex2Color(theme.getNumbersBackground()));
        btn8.setBackground(hex2Color(theme.getNumbersBackground()));
        btn9.setBackground(hex2Color(theme.getNumbersBackground()));
        btnPoint.setBackground(hex2Color(theme.getNumbersBackground()));
        btnC.setBackground(hex2Color(theme.getOperatorBackground()));
        btnBack.setBackground(hex2Color(theme.getOperatorBackground()));
        btnMod.setBackground(hex2Color(theme.getOperatorBackground()));
        btnDiv.setBackground(hex2Color(theme.getOperatorBackground()));
        btnMul.setBackground(hex2Color(theme.getOperatorBackground()));
        btnSub.setBackground(hex2Color(theme.getOperatorBackground()));
        btnAdd.setBackground(hex2Color(theme.getOperatorBackground()));
        btnRoot.setBackground(hex2Color(theme.getOperatorBackground()));
        btnLog.setBackground(hex2Color(theme.getOperatorBackground()));
        btnPower.setBackground(hex2Color(theme.getOperatorBackground()));
        btnEqual.setBackground(hex2Color(theme.getBtnEqualBackground()));
    }
}