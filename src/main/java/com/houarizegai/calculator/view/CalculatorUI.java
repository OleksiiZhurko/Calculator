package com.houarizegai.calculator.view;

import com.houarizegai.calculator.data.AppDefaults;
import com.houarizegai.calculator.data.Theme;
import com.houarizegai.calculator.service.Calculation;
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

    private static final String DOUBLE_OR_NUMBER_REGEX = "(-?\\d+[.]\\d*)|(\\d+)|(-\\d+)";
    private static final String INITIAL_VALUE = "0";
    private static final String EMPTY_VALUE = "";

    private final Calculation calculation;
    private JFrame window;
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

    private Buttons selectedOperator;
    private boolean shouldProceed = true; // For calculate with Opt != (=)
    private boolean shouldAddToDisplay = true; // Connect numbers in display
    private double typedValue = 0;

    private final Map<String, Theme> themesMap;
    private final AppDefaults appDefaults;

    public CalculatorUI(
            Calculation calculation,
            AppDefaults appDefaults,
            Map<String, Theme> themesMap
    ) {
        this.calculation = calculation;
        this.appDefaults = appDefaults;
        this.themesMap = themesMap;
    }

    public void start() {
        window = new JFrame(appDefaults.getApplicationTitle());
        window.setSize(appDefaults.getWindowWidth(), appDefaults.getWindowHeight());
        window.setLocationRelativeTo(null);

        int[] columns = {
                appDefaults.getMarginX(),
                appDefaults.getMarginX() + 90,
                appDefaults.getMarginX() + 90 * 2,
                appDefaults.getMarginX() + 90 * 3,
                appDefaults.getMarginX() + 90 * 4
        };
        int[] rows = {
                appDefaults.getMarginY(),
                appDefaults.getMarginY() + 100,
                appDefaults.getMarginY() + 100 + 80,
                appDefaults.getMarginY() + 100 + 80 * 2,
                appDefaults.getMarginY() + 100 + 80 * 3,
                appDefaults.getMarginY() + 100 + 80 * 4
        };

        initInputScreen(columns, rows);
        initButtons(columns, rows);
        initCalculatorTypeSelector();

        initThemeSelector();

        window.setLayout(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    private void initThemeSelector() {
        comboTheme = createComboBox(themesMap.keySet().toArray(new String[0]), 230, 30, "Theme");
        comboTheme.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String selectedTheme = (String) event.getItem();
                applyTheme(themesMap.get(selectedTheme));
            }
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
        inputScreen.setFont(new Font(appDefaults.getFontName(), Font.PLAIN, 33));
        window.add(inputScreen);
    }

    private void initCalculatorTypeSelector() {
        comboCalculatorType = createComboBox(new String[]{"Standard", "Scientific"}, 20, 30, "Calculator type");
        comboCalculatorType.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String selectedItem = (String) event.getItem();
                switch (selectedItem) {
                    case "Standard":
                        window.setSize(appDefaults.getWindowWidth(), appDefaults.getWindowHeight());
                        btnRoot.setVisible(false);
                        btnPower.setVisible(false);
                        btnLog.setVisible(false);
                        break;
                    case "Scientific":
                        window.setSize(appDefaults.getWindowWidth() + 80, appDefaults.getWindowHeight());
                        btnRoot.setVisible(true);
                        btnPower.setVisible(true);
                        btnLog.setVisible(true);
                        break;
                }
            }
        });
    }

    private void initButtons(int[] columns, int[] rows) {
        btnC = createButton(Buttons.RESET.title, columns[0], rows[1]);
        btnC.addActionListener(event -> {
            inputScreen.setText(INITIAL_VALUE);
            selectedOperator = null;
            typedValue = 0;
        });

        btnBack = createButton(Buttons.BACKSPACE.title, columns[1], rows[1]);
        btnBack.addActionListener(event -> {
            String str = inputScreen.getText();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < (str.length() - 1); i++) {
                stringBuilder.append(str.charAt(i));
            }
            if (stringBuilder.toString().equals(EMPTY_VALUE)) {
                inputScreen.setText(INITIAL_VALUE);
            } else {
                inputScreen.setText(stringBuilder.toString());
            }
        });

        btnMod = createButton(Buttons.PERCENT.title, columns[2], rows[1]);
        btnMod.addActionListener(event -> {
            if (Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()) && shouldProceed) {
                typedValue = calculation.calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = Buttons.PERCENT;
                shouldProceed = false;
                shouldAddToDisplay = false;
            }
        });

        btnDiv = createOperationButton(Buttons.DIV, columns[3], rows[1]);
        btn7 = createNumberButton(Buttons.SEVEN.title, columns[0], rows[2]);
        btn8 = createNumberButton(Buttons.EIGHT.title, columns[1], rows[2]);
        btn9 = createNumberButton(Buttons.NINE.title, columns[2], rows[2]);
        btnMul = createOperationButton(Buttons.MULT, columns[3], rows[2]);
        btn4 = createNumberButton(Buttons.FOUR.title, columns[0], rows[3]);
        btn5 = createNumberButton(Buttons.FIVE.title, columns[1], rows[3]);
        btn6 = createNumberButton(Buttons.SIX.title, columns[2], rows[3]);
        btnSub = createOperationButton(Buttons.MINUS, columns[3], rows[3]);
        btn1 = createNumberButton(Buttons.ONE.title, columns[0], rows[4]);
        btn2 = createNumberButton(Buttons.TWO.title, columns[1], rows[4]);
        btn3 = createNumberButton(Buttons.THREE.title, columns[2], rows[4]);
        btnAdd = createOperationButton(Buttons.ADD, columns[3], rows[4]);

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

        btn0 = createNumberButton(Buttons.ZERO.title, columns[1], rows[5]);

        btnEqual = createButton(Buttons.EQUAL.title, columns[2], rows[5]);
        btnEqual.addActionListener(event -> {
            if (Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText())) {
                if (shouldProceed) {
                    typedValue = calculation.calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                    if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                        inputScreen.setText(String.valueOf((int) typedValue));
                    } else {
                        inputScreen.setText(String.valueOf(typedValue));
                    }
                    selectedOperator = Buttons.EQUAL;
                    shouldAddToDisplay = false;
                }
            }
        });
        btnEqual.setSize(2 * appDefaults.getButtonWidth() + 10, appDefaults.getButtonHeight());

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

        btnPower = createOperationButton(Buttons.POW, columns[4], rows[2]);
        btnPower.setFont(new Font(appDefaults.getFontName(), Font.PLAIN, 24));
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
        btn.setBounds(x, y, appDefaults.getButtonWidth(), appDefaults.getButtonHeight());
        btn.setFont(new Font(appDefaults.getFontName(), Font.PLAIN, 28));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusable(false);
        window.add(btn);

        return btn;
    }

    private JButton createNumberButton(String title, int pColumn, int pRow) {
        JButton button = createButton(title, pColumn, pRow);
        button.addActionListener(event -> {
            if (shouldAddToDisplay && !Pattern.matches("0*", inputScreen.getText())) {
                inputScreen.setText(inputScreen.getText() + title);
            } else {
                inputScreen.setText(title);
                shouldAddToDisplay = true;
            }
            shouldProceed = true;
        });
        return button;
    }

    private JButton createOperationButton(Buttons operation, int pColumn, int pRow) {
        JButton button = createButton(operation.title, pColumn, pRow);
        button.addActionListener(event -> {
            if (Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText())) {
                if (shouldProceed) {
                    typedValue = calculation.calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                    if (Pattern.matches("-?\\d+[.]0*", String.valueOf(typedValue))) {
                        inputScreen.setText(String.valueOf((int) typedValue));
                    } else {
                        inputScreen.setText(String.valueOf(typedValue));
                    }
                    selectedOperator = operation;
                    shouldProceed = false;
                    shouldAddToDisplay = false;
                } else {
                    selectedOperator = operation;
                }
            }
        });
        return button;
    }

    private void applyTheme(Theme theme) {
        Color applicationBackgroundColor = hex2Color(theme.getApplicationBackground());
        Color foregroundColor = hex2Color(theme.getTextColor());
        Color numbersBackgroundColor = hex2Color(theme.getNumbersBackground());
        Color operatorBackgroundColor = hex2Color(theme.getOperatorBackground());
        Color btnEqualBackgroundColor = hex2Color(theme.getBtnEqualBackground());

        window.getContentPane().setBackground(applicationBackgroundColor);

        comboCalculatorType.setForeground(foregroundColor);
        comboTheme.setForeground(foregroundColor);
        inputScreen.setForeground(foregroundColor);
        btn0.setForeground(foregroundColor);
        btn1.setForeground(foregroundColor);
        btn2.setForeground(foregroundColor);
        btn3.setForeground(foregroundColor);
        btn4.setForeground(foregroundColor);
        btn5.setForeground(foregroundColor);
        btn6.setForeground(foregroundColor);
        btn7.setForeground(foregroundColor);
        btn8.setForeground(foregroundColor);
        btn9.setForeground(foregroundColor);
        btnPoint.setForeground(foregroundColor);
        btnC.setForeground(foregroundColor);
        btnBack.setForeground(foregroundColor);
        btnMod.setForeground(foregroundColor);
        btnDiv.setForeground(foregroundColor);
        btnMul.setForeground(foregroundColor);
        btnSub.setForeground(foregroundColor);
        btnAdd.setForeground(foregroundColor);
        btnRoot.setForeground(foregroundColor);
        btnLog.setForeground(foregroundColor);
        btnPower.setForeground(foregroundColor);
        btnEqual.setForeground(foregroundColor);

        comboCalculatorType.setBackground(applicationBackgroundColor);
        comboTheme.setBackground(applicationBackgroundColor);
        inputScreen.setBackground(applicationBackgroundColor);
        btn0.setBackground(numbersBackgroundColor);
        btn1.setBackground(numbersBackgroundColor);
        btn2.setBackground(numbersBackgroundColor);
        btn3.setBackground(numbersBackgroundColor);
        btn4.setBackground(numbersBackgroundColor);
        btn5.setBackground(numbersBackgroundColor);
        btn6.setBackground(numbersBackgroundColor);
        btn7.setBackground(numbersBackgroundColor);
        btn8.setBackground(numbersBackgroundColor);
        btn9.setBackground(numbersBackgroundColor);
        btnPoint.setBackground(numbersBackgroundColor);
        btnC.setBackground(operatorBackgroundColor);
        btnBack.setBackground(operatorBackgroundColor);
        btnMod.setBackground(operatorBackgroundColor);
        btnDiv.setBackground(operatorBackgroundColor);
        btnMul.setBackground(operatorBackgroundColor);
        btnSub.setBackground(operatorBackgroundColor);
        btnAdd.setBackground(operatorBackgroundColor);
        btnRoot.setBackground(operatorBackgroundColor);
        btnLog.setBackground(operatorBackgroundColor);
        btnPower.setBackground(operatorBackgroundColor);
        btnEqual.setBackground(btnEqualBackgroundColor);
    }
}