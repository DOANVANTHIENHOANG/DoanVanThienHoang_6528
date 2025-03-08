
package doanvantthienhoang_6528;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Calculator extends JFrame {
 private static JTextField display;
 private static double result = 0;
 private static String lastCommand = "=";
 private static boolean start = true;
 private boolean isDarkMode = false;
 private JPanel buttonPanel;
 public Calculator(){
     setTitle("Calulator");
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLayout(new BorderLayout());
     display = new JTextField("0");
     display.setEditable(false);
     display.setFont(new Font( "Arial",Font.BOLD,  28));
     display.setHorizontalAlignment(JTextField.RIGHT);
     add(display, BorderLayout.NORTH);
    JPanel buttonPanel = new JPanel();
     buttonPanel.setLayout(new GridLayout(6,4,10,10));
     String[] buttons = {
         "C","x³","%","/",
         "7","8","9","*",
         "4","5","6","-",
         "1","2","3","+",
         "0",".","=","√",
         "Drark","π","e","X!"
     };
     for(String text : buttons){
         JButton button = new JButton(text);
         button.setFont(new Font("Arial",Font.BOLD,24));
         button.setBackground(Color.LIGHT_GRAY);
         button.setOpaque(true);
         button.setBorderPainted(false);
         button.addMouseListener(new MouseAdapter(){
         @Override
         public void mouseEntered(MouseEvent e){
             button.setBackground(Color.GRAY);
             
         }
         @Override
        public void mouseExited(MouseEvent e){ 
            button.setBackground(Color.LIGHT_GRAY);
             }
         });
         
     buttonPanel.add(button);
     if(Character.isDigit(text.charAt(0)) || text.equals(".")){
         button.addActionListener( new NumberListener());
     } else if(text.equals("C")){
         button.addActionListener( e -> {
             start =true;
             result = 0;
             lastCommand = "=";
             display.setText("0");
         });
     }else if (text.equals("Dark")){
         button.addActionListener(e -> toggleDarkMode());
     }else if (text.equals("π")){
         button.addActionListener(e -> display.setText(String.valueOf(Math.PI)));
     }else if (text.equals("e")){
         button.addActionListener(e -> display.setText(String.valueOf(Math.E)));
     }else{
         button.addActionListener(new OperatorListener());
     }   
     }
     add(buttonPanel, BorderLayout.CENTER);
     setSize(400,650);
     setVisible(true);
    }
    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        getContentPane().setBackground(isDarkMode ? Color.DARK_GRAY : Color.WHITE);
        display.setBackground(isDarkMode ? Color.BLACK : Color.WHITE);
        display.setForeground(isDarkMode ? Color.GREEN : Color.BLACK);
        buttonPanel.setBackground(isDarkMode ? Color.DARK_GRAY : Color.WHITE);
    }
    private class NumberListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event) {
         String digit = event.getActionCommand();
         if(start){
             display.setText(digit);
             start = false;
         }else{
             display.setText(display.getText() + digit);
         }
        }
        
    }
    private class OperatorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
           String command = event.getActionCommand();
         double value = Double.parseDouble(display.getText());
           switch(command){
               case "√":
                   display.setText(value >= 0 ? formatResult(Math.sqrt(value)) : "Loi");
                   break;
               case "%":
                   display.setText(formatResult(value / 100));
                   break;
               case "x³":
                   display.setText(formatResult(Math.pow(value, 3)));
               case "X!":
                   display.setText(value >= 0 ? String.valueOf(factorial((int) value)) : "loi");
                   break;
               default :
                   if(!start){
                       calculate(value);
                   }
                   display.setText(display.getText() + " " + command + " ");
                   lastCommand = command;
                   start = true;
           }
    }
    
}
    private long factorial (int n){
        if(n == 0 || n == 1 ) return 1;
        long result = 1;
        for(int i = 2; i<= n;i++ ){
            result *= i;
            }
        return result;
    }
private  void calculate(double x){
     switch(lastCommand){
         case "+" -> result += x;
         case "-" -> result -=x;
         case "*" -> result *= x;
         case "/" -> {
             if(x != 0) result /= x;
             else display.setText("Loi: Chia cho 0" );
         }
         case "=" -> result = x;
     }
     if(!display.getText().startsWith("Loi")){
         display.setText(formatResult(result));
     }
}
 private static String formatResult(double value){
     if(value == (long) value){
         return String.format("%d", (long) value);
     }else{
         return String.format("%.10g", value);
     }
 }   
public static void main (String[] args){
    Calculator calculator = new Calculator();
}
}




