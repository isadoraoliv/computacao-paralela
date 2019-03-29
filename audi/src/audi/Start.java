package audi;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.util.Random;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
*
* @author Isadora, Luis e Samara
*/

public class Start extends javax.swing.JFrame {

    public Start() {
        
    	initComponents();
        
        ProductCarroceria carroceria = new ProductCarroceria();
        ProductPneu pneu = new ProductPneu();
        ProductBanco banco = new ProductBanco();
        ProductEletronica eletronica = new ProductEletronica();
        ProductMotor motor = new ProductMotor();
               
        jTextArea1.setText("0/20"); jTextArea3.setText("0/100"); jTextArea4.setText("0/25");
        jTextArea5.setText("0/10"); jTextArea6.setText("0/8");
        
        Color defineCor = new Color(67,187,167);
        
        this.getContentPane().setBackground(Color.WHITE);    
        this.setVisible(true);
    }
    
    int stop ;
    
//Produtor ===============
    
    public class Produtor implements Runnable{
    
    private PonteSincronizada sincronia;
    private int estoque;
    private int tempo;
    private int quantidade;
    private String nome;
    
    public Produtor(PonteSincronizada sincronia, int estoque, int tempo, String nome, int quantidade ){
        this.sincronia = sincronia;
        this.estoque = estoque;
        this.tempo = tempo;
        this.nome = nome;
        this.quantidade = quantidade;
    }
    
    @Override
    public void run() {
        int soma = 0;
        int estq = estoque;
        Random ger = new Random();
        int nu;
       
        for (int i = 1; ; i++) {
            try { 
                Thread.sleep(tempo);
                soma += i;           
                sincronia.set(i, nome, quantidade);
               

            } catch (InterruptedException ex) {}
            
            if(nome == "Eletronica" && i == estoque){
                    if(eletronica > estoque){
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        i = eletronica - quantidade - carroProduct - 1;
                    }
                    else { i = estoque -((carroProduct-1) * quantidade) -1; }        
                   
            }
            if(nome == "Motor" && i == estoque) { i = 0; }
            if(nome == "Banco" && i == estoque) { i = estoque -((carroProduct-1) * quantidade) -1; }
            if(nome == "Pneu" && i == estoque) {  i = estoque -((carroProduct-1) * quantidade) -1; }
            if(nome == "Carroceria" && i == estoque) { i = estoque -((carroProduct-1) * quantidade) -1; }          
        }       
    }  
}     

//Consumidor =====================
    
    public class Consumidor implements Runnable {
    
    private PonteSincronizada sincronia;
    private int estoque;
    private int quantidade;
    
    public Consumidor(PonteSincronizada sincronia, int estoque, int quantidade){
        this.sincronia = sincronia;
        this.estoque = estoque;
        this.quantidade = quantidade;   
    }
    
    @Override
    public void run() {
        int soma = 0;
        int sinal = estoque/quantidade;
        for (int i = 1; ; i++) {
            try {
                Thread.sleep(500);
                sincronia.get();
            } catch (InterruptedException ex) {}    
        }         
    }   
}
    int cegonha = 1;
    public void ImageIcon(int img){
         ImageIcon auxilar = new ImageIcon(getClass().getResource("/audi/imagens/carrofinal.png"));
         ImageIcon aux = new ImageIcon(getClass().getResource("/audi/imagens/cegonha.png"));
         
         if(img == 1) { jLabel17.setIcon(auxilar); }
         if(img == 2) { jLabel18.setIcon(auxilar); }
         if(img == 3) { jLabel25.setIcon(auxilar); }
         if(img == 4) { jLabel26.setIcon(auxilar); }
         if(img == 5) { jLabel23.setIcon(auxilar); }
         if(img == 6) { jLabel21.setIcon(auxilar); }
         if(img == 7) { jLabel19.setIcon(auxilar); }
         if(img == 8) { jLabel24.setIcon(auxilar); }
         
         if(img == 9) {            
             String cegonhas = String.valueOf(cegonha);
             jLabel22.setIcon(aux);
             jTextArea7.setText("Cegonha: "+cegonhas);
             cegonha = Integer.parseInt(cegonhas);
             cegonha++;
             jLabel17.setIcon(null);
             jLabel18.setIcon(null);
             jLabel25.setIcon(null);
             jLabel26.setIcon(null);
             jLabel23.setIcon(null);
             jLabel21.setIcon(null);
             jLabel19.setIcon(null);
             jLabel24.setIcon(null);
             carroProduct = 1;
             carroceria = 0;
             pneu = 0;
             banco = 0;
             motor = 0;
             eletronica = 0;
             t = 0;
         }
    }
    int carroceria, pneu, banco, motor, eletronica, t = 0;
    int carroProduct = 1;

//Sincronia ===============
    
    public class PonteSincronizada {
    
    private int valor;
    private String nome;
    private int quantidade;
    private boolean buffer = false;
    private int estoque;
    
    int xCarroceria;
    int xMotor;
    int xBanco;
    int xPneu;
    int xEletronica;
    
   // @Override
    public synchronized void set(int valor, String nome, int quantidade) throws InterruptedException {
        while(buffer){
            System.out.println("Aguardar montagem...");
            jTextArea2.append("\nAguardar montagem...\n");
            jTextArea2.setCaretPosition(jTextArea2.getText().length());            
            wait();
        }
        
        System.out.println("Produziu "+valor+" "+nome+"\n");
        jTextArea2.append("\nProduziu "+valor+" "+nome+"\n");
        jTextArea2.setCaretPosition(jTextArea2.getText().length());
        if(stop == 1){
            JOptionPane.showMessageDialog(null, "Clique em -> OK <- e continue a PRODUÇÃO!");
           // System.exit(0);
            stop = 0;
        }
        
        if(nome == "Carroceria"){
            String car = String.valueOf(valor);
            jTextArea1.setText(car+"/20");
            xCarroceria = Integer.parseInt(car);
        }
        if(nome == "Pneu"){
            String pn = String.valueOf(valor);
            jTextArea3.setText(pn+"/100");
            xPneu = Integer.parseInt(pn);
        }
        if(nome == "Banco"){
            String ban = String.valueOf(valor);
            jTextArea4.setText(ban+"/25");
            xBanco = Integer.parseInt(ban);
        }
        if(nome == "Motor"){
            String mo = String.valueOf(valor);
            jTextArea5.setText(mo+"/10");
            xMotor = Integer.parseInt(mo);
        }
        if(nome == "Eletronica"){
            String eletro = String.valueOf(valor);
            jTextArea6.setText(eletro+"/8");
            xEletronica = Integer.parseInt(eletro);
        }
        this.valor = valor;
   
        if (valor % quantidade == 0) {
            buffer = true;
            notifyAll();
            if (nome == "Carroceria") {
                carroceria++;
            }
            if (nome == "Pneu") {
                pneu++;
            }
            if (nome == "Banco") {
                banco++;
            }
            if (nome == "Motor") {
                motor++;
            }
            if (nome == "Eletronica") {
                eletronica++;
            }

            //System.err.println("\t\t\t ACESSORIOS: carroceria,pneu,banco,motor,eletronica: "+carroceria+" "+pneu+" "+banco+" "+motor+" "+eletronica);
            //System.out.println("ACESSORIOS\n"+ "\nEletronica: "+eletronica+ "\nMotor: "+motor+ "\nCarroceria: "+carroceria+ "\nPneu: "+pneu+ "\nBanco: "+banco+"\n");	
            
            if(carroceria >= carroProduct && pneu >= carroProduct && banco >= carroProduct && motor >= carroProduct && 
            		eletronica >= carroProduct){
                System.out.println("+++ PRODUZIU UM CARRO!");
                jTextArea2.append("\n+++ PRODUZIU UM CARRO!\n");
                jTextArea2.setCaretPosition(jTextArea2.getText().length());
                ImageIcon(carroProduct);
                Thread.sleep(3000);
                carroProduct++;
                if(carroProduct == 9){
                    ImageIcon(carroProduct);
                }  
            }
        }     
    }
  
   // @Override
    public synchronized int get() throws InterruptedException {
        
        while(!buffer){
            System.err.println("Linha de produção, aguarde...");
            jTextArea2.append("\nLinha de produção, aguarde...\n");
            jTextArea2.setCaretPosition(jTextArea2.getText().length());
            wait();
        }
        System.err.println("\nEnvio para montagem do carro... ");
        jTextArea2.append("\nEnvio para montagem do carro...\n");
        jTextArea2.setCaretPosition(jTextArea2.getText().length());
        buffer = false;
        //Thread.sleep(500);
        notifyAll();
        return valor;        
    }
}

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel1.setBackground(Color.WHITE);
        jLabel1.setForeground(Color.WHITE);
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jButton1.setForeground(Color.WHITE);
        jButton1.setBackground(new Color(128, 0, 0));
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton2.setForeground(Color.WHITE);
        jButton2.setBackground(new Color(128, 0, 0));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Automoveis Divinopolis - AUDI");
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/audi/imagens/logo.png"))); 
        jLabel1.setBorder(null);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); 
        jLabel6.setText("Carroceria");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); 
        jLabel7.setText("Pneu");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); 
        jLabel8.setText("Banco");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); 
        jLabel9.setText("Motor");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); 
        jLabel10.setText("Eletronica");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); 
        jButton1.setText("Produzir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jTextArea3.setColumns(0);
        jTextArea3.setRows(0);
        jScrollPane3.setViewportView(jTextArea3);

        jTextArea4.setColumns(0);
        jTextArea4.setRows(0);
        jScrollPane4.setColumnHeaderView(jTextArea4);

        jTextArea6.setColumns(0);
        jTextArea6.setRows(0);
        jScrollPane6.setViewportView(jTextArea6);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/audi/imagens/audi.png"))); 

        jTextArea7.setBackground(new java.awt.Color(67, 187, 167));
        jTextArea7.setColumns(0);
        jTextArea7.setFont(new java.awt.Font("Monospaced", 1, 14)); 
        jTextArea7.setRows(0);
        jTextArea7.setBorder(null);
        jTextArea7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextArea7.setOpaque(false);
        jTextArea7.setRequestFocusEnabled(false);
        jTextArea7.setVerifyInputWhenFocusTarget(false);
        jScrollPane7.setViewportView(jTextArea7);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); 
        jButton2.setText("Parar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(68)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jLabel16)
        					.addGap(25))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel6)
        						.addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel10, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addComponent(jScrollPane3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        						.addComponent(jScrollPane1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        								.addComponent(jScrollPane6, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        								.addComponent(jScrollPane4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        								.addComponent(jScrollPane5, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
        							.addPreferredGap(ComponentPlacement.RELATED)))
        					.addGap(199)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addComponent(jLabel24, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel23, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel21, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel26, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addComponent(jLabel19, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
        						.addGroup(layout.createParallelGroup(Alignment.LEADING)
        							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        								.addComponent(jLabel18, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel25, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
        							.addComponent(jLabel17, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        						.addComponent(jLabel22, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(jScrollPane7, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
        							.addGap(39)))
        					.addGap(15))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jButton1)
        					.addGap(18)
        					.addComponent(jButton2)
        					.addGap(37)))
        			.addGap(15))
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 1213, Short.MAX_VALUE)
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(27)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jLabel16, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
        							.addGap(39))
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        										.addComponent(jLabel25, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        										.addComponent(jLabel24, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addComponent(jLabel18, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
        										.addComponent(jLabel23, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        										.addComponent(jLabel17, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        										.addComponent(jLabel21, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        										.addComponent(jLabel19, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
        										.addComponent(jLabel26, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)))
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addComponent(jLabel6)
        										.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(jLabel7)
        										.addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addComponent(jLabel9)
        										.addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addGroup(layout.createSequentialGroup()
        											.addGap(18)
        											.addComponent(jLabel8))
        										.addGroup(layout.createSequentialGroup()
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addGroup(layout.createSequentialGroup()
        											.addGap(18)
        											.addComponent(jLabel10))
        										.addGroup(layout.createSequentialGroup()
        											.addPreferredGap(ComponentPlacement.RELATED)
        											.addComponent(jScrollPane6, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))))
        							.addGap(89)))
        					.addGap(27))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jButton2)
        						.addComponent(jButton1))
        					.addGap(98)
        					.addComponent(jLabel22, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jScrollPane7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addGap(53)))
        			.addGap(3)
        			.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        jTextArea5 = new javax.swing.JTextArea();
        jScrollPane5.setViewportView(jTextArea5);
        
                jTextArea5.setColumns(0);
                jTextArea5.setRows(0);
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane1.setViewportView(jTextArea1);
        
                jTextArea1.setColumns(0);
                jTextArea1.setRows(0);
        getContentPane().setLayout(layout);

        pack();
        setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        PonteSincronizada sincronia = new PonteSincronizada();

        ProductCarroceria carroceria = new ProductCarroceria();
        ProductPneu pneu = new ProductPneu();
        ProductBanco banco = new ProductBanco();
        ProductEletronica eletronica = new ProductEletronica();
        ProductMotor motor = new ProductMotor();

        new Thread(new Produtor(sincronia, carroceria.getEstoque(), carroceria.getTempo(), carroceria.getNome(), carroceria.getQuantidade())).start();
        new Thread(new Consumidor(sincronia, carroceria.getEstoque(), carroceria.getQuantidade())).start();
        new Thread(new Produtor(sincronia, pneu.getEstoque(), pneu.getTempo(), pneu.getNome(), pneu.getQuantidade())).start();
        new Thread(new Consumidor(sincronia, pneu.getEstoque(), pneu.getQuantidade())).start();
        new Thread(new Produtor(sincronia, banco.getEstoque(), banco.getTempo(), banco.getNome(), banco.getQuantidade())).start();
        new Thread(new Consumidor(sincronia, banco.getEstoque(), banco.getQuantidade())).start();
        new Thread(new Produtor(sincronia, eletronica.getEstoque(), eletronica.getTempo(), eletronica.getNome(), eletronica.getQuantidade())).start();
        new Thread(new Consumidor(sincronia, eletronica.getEstoque(), eletronica.getQuantidade())).start();
        new Thread(new Produtor(sincronia, motor.getEstoque(), motor.getTempo(), motor.getNome(), motor.getQuantidade())).start();
        new Thread(new Consumidor(sincronia, motor.getEstoque(), motor.getQuantidade())).start();

    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        stop = 1;
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Start().setVisible(true);
                               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    public javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    public javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    public javax.swing.JTextArea jTextArea3;
    public javax.swing.JTextArea jTextArea4;
    public javax.swing.JTextArea jTextArea5;
    public javax.swing.JTextArea jTextArea6;
    public javax.swing.JTextArea jTextArea7;
}
