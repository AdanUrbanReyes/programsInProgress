package interfazUser;
public class Interfaz extends javax.swing.JFrame {
    public Interfaz() {
        initComponents();
        setTitle("ayaNaster");
        setVisible(true);
    }
    public static String selectDirectory(String title){
        int error;
        String route=null;
        javax.swing.JFileChooser selector=new javax.swing.JFileChooser();
        selector.setDialogTitle(title);//put title of window del explorador de files
        selector.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);//just cant select a carpetas no archivos
        do{
            error=selector.showOpenDialog(null);
            if(error==javax.swing.JFileChooser.APPROVE_OPTION){
                route=selector.getSelectedFile().getAbsolutePath();//selector.getCurrentDirectory().getAbsolutePath();
            }else{
                if(error==javax.swing.JFileChooser.CANCEL_OPTION){
                    javax.swing.JOptionPane.showMessageDialog(null,"is necesary that select a folder to share");
                }
            }
        }while(route==null);
        return route;
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label = new javax.swing.JLabel();
        stringFileSearch = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        tabbedPane = new javax.swing.JTabbedPane();
        fileFounded = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        filesFounded = new javax.swing.JTable();
        downloads = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fileDownloads = new javax.swing.JTable();
        seeServersFree = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        label.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        label.setForeground(java.awt.Color.blue);
        label.setText("Enter string of file to search:");

        stringFileSearch.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        stringFileSearch.setForeground(java.awt.Color.magenta);

        search.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        search.setForeground(java.awt.Color.red);
        search.setText("Search");

        filesFounded.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        filesFounded.setForeground(java.awt.Color.magenta);
        filesFounded.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Size", "MD5", "Servers"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(filesFounded);
        if (filesFounded.getColumnModel().getColumnCount() > 0) {
            filesFounded.getColumnModel().getColumn(0).setPreferredWidth(200);
            filesFounded.getColumnModel().getColumn(1).setPreferredWidth(70);
            filesFounded.getColumnModel().getColumn(2).setPreferredWidth(400);
            filesFounded.getColumnModel().getColumn(3).setPreferredWidth(70);
            filesFounded.getColumnModel().getColumn(3).setMaxWidth(70);
        }

        javax.swing.GroupLayout fileFoundedLayout = new javax.swing.GroupLayout(fileFounded);
        fileFounded.setLayout(fileFoundedLayout);
        fileFoundedLayout.setHorizontalGroup(
            fileFoundedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE)
        );
        fileFoundedLayout.setVerticalGroup(
            fileFoundedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
        );

        tabbedPane.addTab("FileFounded", fileFounded);

        fileDownloads.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        fileDownloads.setForeground(java.awt.Color.magenta);
        fileDownloads.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Size", "File", "Remove"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(fileDownloads);
        if (fileDownloads.getColumnModel().getColumnCount() > 0) {
            fileDownloads.getColumnModel().getColumn(0).setPreferredWidth(200);
            fileDownloads.getColumnModel().getColumn(1).setPreferredWidth(70);
            fileDownloads.getColumnModel().getColumn(2).setPreferredWidth(400);
            fileDownloads.getColumnModel().getColumn(3).setPreferredWidth(70);
            fileDownloads.getColumnModel().getColumn(3).setMaxWidth(70);
        }

        javax.swing.GroupLayout downloadsLayout = new javax.swing.GroupLayout(downloads);
        downloads.setLayout(downloadsLayout);
        downloadsLayout.setHorizontalGroup(
            downloadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downloadsLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        downloadsLayout.setVerticalGroup(
            downloadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downloadsLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Downloads", downloads);

        seeServersFree.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        seeServersFree.setForeground(java.awt.Color.pink);
        seeServersFree.setText("See Servers Free");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label)
                        .addGap(18, 18, 18)
                        .addComponent(stringFileSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(seeServersFree)
                            .addComponent(search))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(seeServersFree)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label)
                    .addComponent(stringFileSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel downloads;
    public javax.swing.JTable fileDownloads;
    private javax.swing.JPanel fileFounded;
    public javax.swing.JTable filesFounded;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel label;
    public javax.swing.JButton search;
    public javax.swing.JButton seeServersFree;
    public javax.swing.JTextField stringFileSearch;
    public javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
}
