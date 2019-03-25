package edu.stts.orderapputs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText edName;
    private RadioGroup rgType;
    private RadioButton rbTea, rbCoffee, rbSmoothies;
    private CheckBox cbPearl, cbPudding, cbRedBean, cbCoconut;
    private Button btnMinus, btnPlus, btnAdd, btnDelete, btnReset;
    private TextView txtQty, txtTotal, txtName;
    private RecyclerView rvOrder;
    private OrderAdapter adapter;
    private ArrayList<Order> orderList = new ArrayList<Order>();
    private long total = 0;
    private int index = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edName = (EditText) findViewById(R.id.editText_name);
        rgType = (RadioGroup) findViewById(R.id.radioGroup_type);
        rbTea = (RadioButton) findViewById(R.id.radioButton_tea);
        rbCoffee = (RadioButton) findViewById(R.id.radioButton_coffee);
        rbSmoothies = (RadioButton) findViewById(R.id.radioButton_smoothies);
        cbPearl = (CheckBox) findViewById(R.id.checkBox_pearl);
        cbPudding = (CheckBox) findViewById(R.id.checkBox_pudding);
        cbRedBean = (CheckBox) findViewById(R.id.checkBox_red_bean);
        cbCoconut = (CheckBox) findViewById(R.id.checkBox_coconut);
        btnMinus = (Button) findViewById(R.id.button_minus);
        btnPlus = (Button) findViewById(R.id.button_plus);
        txtQty = (TextView) findViewById(R.id.textView_qty);
        btnAdd = (Button) findViewById(R.id.button_add);
        btnDelete = (Button) findViewById(R.id.button_delete);
        btnReset = (Button) findViewById(R.id.button_reset);
        rvOrder = (RecyclerView) findViewById(R.id.rvOrder);
        txtName = (TextView) findViewById(R.id.editText_name);
        txtTotal = (TextView) findViewById(R.id.txtStatus);
        adapter = new OrderAdapter(orderList, new RVClickListener(){
            @Override
            public void recyclerViewClick(View v, int posisi) {
                index = posisi;
                Order temp = orderList.get(index);
                if (temp.getType().equals("Tea")){
                    rbTea.setChecked(true);
                }else if (temp.getType().equals("Coffee")){
                    rbCoffee.setChecked(true);
                }else if (temp.getType().equals("Smoothies")){
                    rbSmoothies.setChecked(true);
                }
                cbPearl.setChecked(false); cbPudding.setChecked(false); cbRedBean.setChecked(false); cbCoconut.setChecked(false);
                for (int i = 0; i < temp.getToppings().size(); i++){
                    if (temp.getToppings().get(i).equals("Pearl")){
                        cbPearl.setChecked(true);
                    }else if (temp.getToppings().get(i).equals("Pudding")){
                        cbPudding.setChecked(true);
                    }else if (temp.getToppings().get(i).equals("Red Bean")){
                        cbRedBean.setChecked(true);
                    }else if (temp.getToppings().get(i).equals("Coconut Jelly")){
                        cbCoconut.setChecked(true);
                    }
                }
                txtQty.setText(temp.getQty()+"");
            }
        });

        total = 0;
        RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this);
        rvOrder.setLayoutManager(lm);
        rvOrder.setAdapter(adapter);

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int jml = Integer.parseInt(txtQty.getText().toString());
                if (jml > 1){
                    jml--;
                }
                txtQty.setText(jml+"");
                index = -1;
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int jml = Integer.parseInt(txtQty.getText().toString());
                jml++;
                txtQty.setText(jml+"");
                index = -1;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edName.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Field Name cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    String name = edName.getText().toString();
                    int subtotal = 0;
                    String type = ((RadioButton) findViewById(rgType.getCheckedRadioButtonId())).getText().toString();
                    if (type.equals("Tea")){
                        subtotal += 23000;
                    }else if (type.equals("Coffee")){
                        subtotal += 25000;
                    }else if (type.equals("Smoothies")){
                        subtotal += 30000;
                    }
                    ArrayList<String> topping = new ArrayList<String>();
                    if (cbPearl.isChecked()){
                        topping.add("Pearl");
                        subtotal += 3000;
                    }
                    if (cbRedBean.isChecked()){
                        topping.add("Red Bean");
                        subtotal += 4000;
                    }
                    if (cbPudding.isChecked()){
                        topping.add("Pudding");
                        subtotal += 3000;
                    }
                    if (cbCoconut.isChecked()){
                        topping.add("Coconut Jelly");
                        subtotal += 4000;
                    }
                    int qty = Integer.parseInt(txtQty.getText().toString());
                    subtotal *= qty;
                    total += subtotal;

                    txtName.setText(name);
                    txtTotal.setText("Hello "+name+" Total: Rp: "+total);
                    orderList.add(new Order(type, topping, qty, subtotal));
                    adapter.notifyDataSetChanged();

                    index = -1;
                    rbTea.setChecked(true);
                    cbPearl.setChecked(false); cbPudding.setChecked(false); cbRedBean.setChecked(false); cbCoconut.setChecked(false);
                    txtQty.setText("1");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index != -1){
                    total -= orderList.get(index).getSubtotal();
                    txtTotal.setText(total + "");
                    orderList.remove(index);
                    adapter.notifyDataSetChanged();

                    index = -1;
                    rbTea.setChecked(true);
                    cbPearl.setChecked(false); cbPudding.setChecked(false); cbRedBean.setChecked(false); cbCoconut.setChecked(false);
                    txtQty.setText("1");
                }else{
                    Toast.makeText(MainActivity.this, "Anda belum memilih item yang akan dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edName.setText("");
                orderList.clear();
                adapter.notifyDataSetChanged();
                txtName.setText("Cust");
                txtTotal.setText("0");
                total = 0;

                index = -1;
                rbTea.setChecked(true);
                cbPearl.setChecked(false); cbPudding.setChecked(false); cbRedBean.setChecked(false); cbCoconut.setChecked(false);
                txtQty.setText("1");
            }
        });
    }
}
