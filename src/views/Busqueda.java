package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;
import com.mysql.cj.x.protobuf.MysqlxResultset.FetchSuspendedOrBuilder;

import factory.CrearConexionFactory;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
		
		tbReservas = new JTable();
		tbReservas.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				Map<String, String> m = new HashMap<>();
				
				if(e.getKeyCode() == 10) {
					System.out.println("Enter");
					System.out.println(modelo.getValueAt(0,3).toString());
					Integer i = panel.getSelectedIndex();
					m.put("ID", modelo.getValueAt(0,0).toString());
					m.put("FECHA_ENTRADA", modelo.getValueAt(0,1).toString());
					m.put("FECHA_SALIDA", modelo.getValueAt(0,2).toString());
					m.put("VALOR", modelo.getValueAt(0,3).toString());
					m.put("FORMA_PAGO", modelo.getValueAt(0,4).toString());
					actualizar(m, i);
				}
			}
		});
		
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		System.out.println(tbReservas.isEditing());
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		
		
		
		tbHuespedes = new JTable();
		tbHuespedes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Map<String, String> m = new HashMap<>();
				
				if(e.getKeyCode() == 10) {
					System.out.println("Enter");
					System.out.println(modeloHuesped.getValueAt(0,3).toString());
					Integer i = panel.getSelectedIndex();
					m.put("ID", modeloHuesped.getValueAt(0,0).toString());
					m.put("NOMBRE", modeloHuesped.getValueAt(0,1).toString());
					m.put("APELLIDOS", modeloHuesped.getValueAt(0,2).toString());
					m.put("FECHA_NACIMIENTO", modeloHuesped.getValueAt(0,3).toString());
					m.put("NACIONALIDAD", modeloHuesped.getValueAt(0,4).toString());
					m.put("TELEFONO", modeloHuesped.getValueAt(0,5).toString());
					m.put("idReserva", modeloHuesped.getValueAt(0,6).toString());
					actualizar(m, i);
				}
			}
		});
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscar(txtBuscar.getText(), panel.getSelectedIndex());
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Integer i = panel.getSelectedIndex();
				if(i == 0) {
					System.out.println(tbReservas.editCellAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()));
				}else {
					System.out.println(tbHuespedes.editCellAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()));
				}				
			}
		});
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel(); 
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer i = panel.getSelectedIndex();
				if(i == 0) {
					eliminar(modelo.getValueAt(tbReservas.getSelectedRow(),0).toString(), i);
				}else {
					eliminar(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(),0).toString(), i);
				}
				
			}
		});
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	
	public void actualizar(Map<String, String> m, Integer i) {
		System.out.println("Actualizando"+i);
		System.out.println(m.get("VALOR"));
		
		if(i == 0) {
			try {
				Connection con= new CrearConexionFactory().recuperaConexion();
				PreparedStatement pt = con.prepareStatement("UPDATE RESERVAS SET"
				+" FECHA_ENTRADA =?"
				+", FECHA_SALIDA = ?"
				+", VALOR= ?"
				+", FORMA_PAGO= ?"
				+" WHERE ID = ?");
				
				pt.setString(1, m.get("FECHA_ENTRADA"));
				pt.setString(2, m.get("FECHA_SALIDA"));
				pt.setString(3, m.get("VALOR"));
				pt.setString(4, m.get("FORMA_PAGO"));
				pt.setInt(5,Integer.valueOf(m.get("ID")));
				
				pt.execute();
				
				Integer actualizado = pt.getUpdateCount();			
				System.out.println("Actualizado "+ actualizado);
				 
				pt.close();
				
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}else {
			try {
				System.out.println(m.get("ID"));
				Connection con= new CrearConexionFactory().recuperaConexion();
				PreparedStatement pr = con.prepareStatement("UPDATE HUESPEDES SET"
						+ " NOMBRE = ? "
						+ ", APELLIDOS = ?"
						+ ", FECHA_NACIMIENTO = ? "
						+ ", NACIONALIDAD = ? "
						+ ", TELEFONO = ? "
						+ ", idReserva = ? "
						+ " WHERE ID = ?; ");
				
				pr.setString(1, m.get("NOMBRE"));
				pr.setString(2, m.get("APELLIDOS"));
				pr.setString(3, m.get("FECHA_NACIMIENTO"));
				pr.setString(4, m.get("NACIONALIDAD"));
				pr.setString(5, m.get("TELEFONO"));
				pr.setInt(6, Integer.valueOf(m.get("idReserva")));
				pr.setInt(7, Integer.valueOf(m.get("ID")));
	
				pr.execute();
				
				Integer actualizado = pr.getUpdateCount();		
				System.out.println("Actualizado "+ actualizado);
				 
				pr.close();
				
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
	public void eliminar(String id, Integer i) {
		if(i == 0) {
			try {
				for (int row = 0; row < tbReservas.getRowCount(); row++) {
		            modelo.removeRow(row);
		        }
					System.out.println(id);
					final Connection con = new CrearConexionFactory().recuperaConexion();
					try(con){
						con.setAutoCommit(false);
						final PreparedStatement stm = con.prepareStatement("DELETE FROM RESERVAS WHERE id = ?");			
						try(stm){
							con.setAutoCommit(false);
							stm.setString(1, id);
							stm.execute();
						    Integer Eliminado = stm.getUpdateCount();
						    JOptionPane.showMessageDialog(null, "El usuario se elimino con exito con éxito, el id es: "+ id);
						    System.out.println("Eliminado "+ Eliminado);
						    con.commit();
						}catch(Exception e) {
							con.rollback();
						}
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			try {
				for (int row = 0; row < tbHuespedes.getRowCount(); row++) {
		            modeloHuesped.removeRow(row);
		        }
					System.out.println(id);
					final Connection con = new CrearConexionFactory().recuperaConexion();
					try(con){
						con.setAutoCommit(false);
						final PreparedStatement stm = con.prepareStatement("DELETE FROM HUESPEDES WHERE id = ?");
						try(stm){
							stm.setString(1, id);
							stm.execute();
						    Integer Eliminado = stm.getUpdateCount();
						    JOptionPane.showMessageDialog(null, "El usuario se elimino con exito con éxito, el id es: "+ id);
						    System.out.println("Eliminado "+ Eliminado);
						    con.commit();
						}catch (Exception e) {
							con.rollback();
						}
					}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	public void buscar(String b, Integer i) {	
		System.out.println("Entero"+ i);
		try {
			if(i == 0) {
				
				for (int row = 0; row < tbReservas.getRowCount(); row++) {
		            modelo.removeRow(row);
		        }
				Connection con = new CrearConexionFactory().recuperaConexion();
				PreparedStatement stm = con.prepareStatement("SELECT ID, FECHA_ENTRADA, FECHA_SALIDA, VALOR, FORMA_PAGO FROM RESERVAS"
						+ " WHERE id = ?");
				stm.setString(1, b);
				ResultSet set = stm.executeQuery();
								
				while(set.next()) {	
					modelo.addRow(new Object[] {set.getInt("ID"), 
							set.getString("FECHA_ENTRADA"),
							set.getString("FECHA_SALIDA"), 
							set.getInt("VALOR"), 
							set.getString("FORMA_PAGO" )});
				}
				con.close();
			}else {
				
				for (int row = 0; row < tbHuespedes.getRowCount(); row++) {
		            modeloHuesped.removeRow(row);
		        }
				
				Connection con = new CrearConexionFactory().recuperaConexion();
				PreparedStatement stm = con.prepareStatement("SELECT ID, NOMBRE, APELLIDOS, FECHA_NACIMIENTO, NACIONALIDAD, TELEFONO, idReserva "
						+ "FROM HUESPEDES WHERE APELLIDOS = ?");
				stm.setString(1, b);
				ResultSet set = stm.executeQuery();
								
				while(set.next()) {
					modeloHuesped.addRow(new Object[] {set.getInt("ID"), 
							set.getString("NOMBRE"),
							set.getString("APELLIDOS"),
							set.getString("FECHA_NACIMIENTO"),
							set.getString("NACIONALIDAD"), 
							set.getString("TELEFONO" ),
							set.getString("idReserva" )});
				}
				con.close();
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//throw new RuntimeException(e);
			JOptionPane.showMessageDialog(this, "ID de reserva o Apellido incorrecto");
		}
		
	}
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
