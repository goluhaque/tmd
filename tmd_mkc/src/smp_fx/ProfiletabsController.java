/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smp_fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by Afzalul Haque on 20-Apr-14.
 */

public class ProfiletabsController implements Initializable {
    static user ud_current;
    static thread ct;
    static place cp;
    static user u;
    static forum f[];
    static category c[];
    static moderator m;
    static ProfiletabsController ptcob;
    HashMap <Hyperlink, thread> ques = new HashMap<Hyperlink, thread>();
    HashMap <Hyperlink, user> uses = new HashMap<Hyperlink, user>();
    HashMap <Hyperlink, forum> forums = new HashMap<Hyperlink, forum>();
    HashMap <Hyperlink, place> places = new HashMap<Hyperlink, place>();
    HashMap <Hyperlink, category> categories = new HashMap<Hyperlink, category>();
    @FXML
    AnchorPane main;
    @FXML
    TabPane tP;
    @FXML
    Tab ud_tab, df_tab;
    SingleSelectionModel<Tab> sM;
    //home variables
    @FXML
    Label home_name,home_email,home_book_label, home_bookb_label;
    @FXML
    Hyperlink home_update, home_ques_hyp1,  home_ques_hyp2, home_ques_hyp3, home_ques_hyp4, home_ques_hyp5;
    @FXML
    Hyperlink home_hv_hyp1, home_hv_hyp2, home_hv_hyp3, home_hv_hyp4, home_hv_hyp5, home_hv_hyp6;
    @FXML
    Label home_nv_label1, home_nv_label2, home_nv_label3, home_nv_label4, home_nv_label5;
    @FXML
    Label home_by_label1, home_by_label2, home_by_label3, home_by_label4, home_by_label5;
    @FXML
    Label home_hv_label1, home_hv_label2, home_hv_label3, home_hv_label4, home_hv_label5, home_hv_label6;
    @FXML
    Hyperlink home_name_hyp1, home_name_hyp2, home_name_hyp3, home_name_hyp4;
    @FXML
    Label home_bb_label1, home_bb_label2, home_bb_label3, home_bb_label4;
    @FXML
    ImageView home_image;

    //user diary variables
    @FXML
    Label ud_name_label, ud_email_label, ud_book_label, ud_bookb_label;
    @FXML
    Hyperlink ud_dog_hyp1, ud_dog_hyp2, ud_dog_hyp3, ud_dog_hyp4;
    @FXML
    Hyperlink ud_dog_hyp5, ud_dog_hyp6;
    @FXML
    Hyperlink ud_ques_hyp1, ud_ques_hyp2, ud_ques_hyp3, ud_ques_hyp4, ud_ques_hyp5;
    @FXML
    Label ud_dog_label1, ud_dog_label2, ud_dog_label3, ud_dog_label4;
    @FXML
    Label ud_dog_label5, ud_dog_label6;
    @FXML
    Label ud_nv_label1, ud_nv_label2, ud_nv_label3, ud_nv_label4, ud_nv_label5, ud_ta_label1, ud_ta_label2, ud_ta_label3, ud_ta_label4, ud_ta_label5;
    @FXML
    Hyperlink ud_name_hyp1, ud_name_hyp2, ud_name_hyp3, ud_name_hyp4;
    @FXML
    Label ud_bookb_label1, ud_bookb_label2, ud_bookb_label3, ud_bookb_label4;
    @FXML
    ImageView image_book, ud_image;

    //Discussions variables
    @FXML
    Label df_topic_label, df_topic_label1, df_title_label, df_ques_label, df_ans_label, d_topinfo_label, df_rq_label1, df_id_label, df_netv;
    @FXML
    Hyperlink df_topic_hyp1, df_topic_hyp2, df_topic_hyp3, df_topic_hyp4, df_topic_hyp5, df_topic_hyp6;
    @FXML
    Hyperlink up_image, down_image;
    @FXML
    ComboBox df_combo;
    @FXML
    TextArea df_ans_text;
    @FXML
    Hyperlink df_ques_hyp1, df_ques_hyp2, df_ques_hyp3, df_ques_hyp4, df_ques_hyp5, df_ques_hyp6;
    @FXML
    Label df_nv_label1, df_nv_label2, df_nv_label3, df_nv_label4, df_nv_label5, df_nv_label6;
    @FXML
    ImageView image_dog;
    @FXML
    AnchorPane df_ap;

    //In & Around Manipal variables
    @FXML
    Label iam_cat_label, iam_cat_label1, iam_title_label, iam_review_label, iam_place_id, iam_loc_label;
    @FXML
    Hyperlink iam_cat_hyp1, iam_cat_hyp2, iam_cat_hyp3, iam_cat_hyp4, iam_cat_hyp5, iam_cat_hyp6;

    @FXML
    Hyperlink iam_place_hyp1, iam_place_hyp2, iam_place_hyp3, iam_place_hyp4, iam_place_hyp5, iam_place_hyp6;
    @FXML
    Label iam_cat_label2, iam_title_label2, iam_rar_label, iam_aanr_label, iam_10_label;
    @FXML
    TextField iam_rating_text;
    @FXML
    TextArea iam_review_text;
    @FXML
    Button iam_review_button;
    @FXML
    ComboBox iam_combo;
    @FXML
    TextField iam_title_text, iam_loc_textf;

    @FXML
    TextField df_title_text;
    @FXML
    TextArea df_ques_text;
    @FXML
    ComboBox ques_cb;
    //post[] home_;


    Label error;            //error label
    Pane pane;              //chat pane
    TextField txf1, txf2;   //chat username, and message input
    TextArea txA;           //chat display


    /*
    @FXML
    Label labcd;
    */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ptcob = this;
        //m = warehouse.getModeratorInstance(1);
        //u = m;
//        u = new user(1);
        sM = tP.getSelectionModel();
        final user ux = u;
        error = new Label();
        error.setLayoutY(30);
        error.setLayoutX(600);
        error.setText("");
        error.setTextFill(Color.RED);
        error.setVisible(false);
        error.setFont(Font.font("System", 20));
        main.getChildren().add(error);

        //chatbox
        pane = new Pane();
        pane.setLayoutY(472);
        pane.setLayoutX(1044);

        txf1 = new TextField();
        txf1.setLayoutY(14);
        txf1.setLayoutX(14);
        txf1.setMaxWidth(200);
        txf1.setMaxHeight(22);
        txf1.setMinWidth(200);
        txf1.setMinHeight(22);
        txf1.setPromptText("Enter username here");
        pane.getChildren().add(txf1);
        txA = new TextArea();
        txA.setLayoutY(47);
        txA.setLayoutX(14);
        txA.setMaxWidth(250);
        txA.setMaxHeight(133);
        txA.setPromptText("Your messages will appear here.");
        pane.getChildren().add(txA);
        txf2 = new TextField();
        txf2.setLayoutY(195);
        txf2.setLayoutX(14);
        txf2.setMaxWidth(250);
        txf2.setMaxHeight(22);
        txf2.setMinWidth(250);
        txf2.setMinHeight(22);
        txf2.setPromptText("Enter message here");
        pane.getChildren().add(txf2);

        txf2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String msg = txf2.getText();
                String user = txf1.getText();
                //System.out.println(user);

                user u2 = random.getUOB(user);
                if(msg == null || msg.equals("") || user.equals("") || user == null || u2 == null) {
                    error.setText("Please enter the fields properly.");
                    error.setVisible(true);
                    return;
                }
                System.out.println("sending to: " + u2.full_name);
                message m = new message(u2, u, msg, new Timestamp(System.currentTimeMillis()));
                ux.sendMessage(msg, u2);
                txA.setText(txA.getText() + "\n\n" + ux.full_name +": " + msg);
            }
        });
        main.getChildren().add(pane);

        final TextField search = new TextField();
        search.setLayoutX(1016);
        search.setLayoutY(37);
        search.setMinWidth(313);
        search.setMinHeight(32);
        search.setPromptText("Search for people. Enter full name.");
        search.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = search.getText();
                if(s == null) {
                    error.setVisible(true);
                    error.setText("Please enter value of search field properly.");
                    return;
                }
                user x = ux.search(s);
                sM.select(ud_tab);
                ud_init(x);
            }
        });
        main.getChildren().add(search);
        if(u != null) {
            try {
                init_home();
                ud_init();
                df_init();
                iam_init();
                if(m != null)
                    moderator();
            } catch (Exception e) {
            }
        }

    }

    @FXML
    void init_home() {
        System.out.println("init_home started");
        Path path2 = Paths.get(u.shadow);
        String s = replace(path2.toString());
        File file2 = new File(s);
        Image image = new Image(file2.toURI().toString());
        home_image.setImage(image);
        home_name.setText(u.full_name);
        home_email.setText(u.email);
        home_book_label.setText(u.fwers + "");
        home_bookb_label.setText(u.fing + "");
        Hyperlink[] home_mv = {home_hv_hyp1, home_hv_hyp2, home_hv_hyp3, home_hv_hyp4, home_hv_hyp5, home_hv_hyp6};
        Hyperlink[] home_name = {home_name_hyp1, home_name_hyp2, home_name_hyp3, home_name_hyp4};
        Hyperlink[] home_ques = {home_ques_hyp1,  home_ques_hyp2, home_ques_hyp3, home_ques_hyp4, home_ques_hyp5};
        Label[] home_mv_l = {home_hv_label1, home_hv_label2, home_hv_label3, home_hv_label4, home_hv_label5, home_hv_label6};
        Label[] home_nv_l = {home_nv_label1, home_nv_label2, home_nv_label3, home_nv_label4, home_nv_label5};
        Label[] home_by_l = {home_by_label1, home_by_label2, home_by_label3, home_by_label4, home_by_label5};
        Label[] home_b_l = {home_bb_label1, home_bb_label2, home_bb_label3, home_bb_label4};

        thread[] t = random.getTop6Ques();

        //System.out.println("T[] received:"+t.length);
        if(t != null)
            for(int i1 = 0;i1<t.length;i1++) {
                home_mv[i1].setText(t[i1].title);
                home_mv_l[i1].setText(""+t[i1].net_votes);
                ques.put(home_mv[i1], t[i1]);
            }
        //System.out.println("display loop for top 6 questions successful");

        user[] ux = random.getTop4User();
        //System.out.println("U[] received:"+ux.length);

        if(ux != null)
            for(int i1 = 0;i1 < ux.length;i1++) {
                home_name[i1].setText(ux[i1].full_name);
                home_b_l[i1].setText(ux[i1].fwers + "");
                uses.put(home_name[i1], ux[i1]);
            }
        //System.out.println("after ux != null");
        post[] p = u.u_f.getFeed();
        //System.out.println("P[] received");
        //if( p == null )
        //    System.out.println("p is null");
        if(p != null) {
            //System.out.println("length of p: " + p.length);
            for (int i1 = 0; i1 < p.length; i1++) {
                //System.out.println("i1 in p: " + i1);
                home_ques[i1].setText(p[i1].content);
                //System.out.println(home_ques[i1].getText());
                home_nv_l[i1].setText(p[i1].t.net_votes + "");
                //System.out.println(home_nv_l[i1].getText());
                home_by_l[i1].setText(p[i1].u.full_name);
                //System.out.println(home_by_l[i1].getText());
                ques.put(home_ques[i1], p[i1].t);
                //System.out.println("exiting after putting in hashmap");
            }
            //System.out.println("reached here");
        }
        System.out.println("ending init_home");
    }

    @FXML
    void ud_init() throws Exception {
        System.out.println("starting ud_init()");
        image_book.setVisible(false);
        Path path2 = Paths.get(u.shadow);
        String s = replace(path2.toString());
        File file2 = new File(s);
        Image image = new Image(file2.toURI().toString());
        //System.out.println(file2.toURI().toString()+"\n"+image);
        ud_image.setImage(image);
        System.out.println("Starting ud_init");
        ud_current = u;
        ud_name_label.setText(u.full_name);
        ud_email_label.setText(u.email);
        ud_book_label.setText(u.fwers + "");
        ud_bookb_label.setText(u.fing + "");
        Hyperlink[] ud_dog = {ud_dog_hyp1, ud_dog_hyp2, ud_dog_hyp3, ud_dog_hyp4, ud_dog_hyp5, ud_dog_hyp6};
        Hyperlink[] ud_name = {ud_name_hyp1, ud_name_hyp2, ud_name_hyp3, ud_name_hyp4};
        Hyperlink[] ud_ques = {ud_ques_hyp1,  ud_ques_hyp2, ud_ques_hyp3, ud_ques_hyp4, ud_ques_hyp5};
        Label[] ud_dog_l = {ud_dog_label1, ud_dog_label2, ud_dog_label3, ud_dog_label4, ud_dog_label5, ud_dog_label6};
        Label[] ud_nv_l = {ud_nv_label1, ud_nv_label2, ud_nv_label3, ud_nv_label4, ud_nv_label5};
        Label[] ud_ta_l = {ud_ta_label1, ud_ta_label2, ud_ta_label3, ud_ta_label4, ud_ta_label5};
        Label[] ud_b_l = {ud_bookb_label1, ud_bookb_label2, ud_bookb_label3, ud_bookb_label4};

        post[] p = u.u_p.getDogearedPosts();
        //System.out.println("p is received");
        if(p != null)
            for(int i1 = 0 ; i1 < p.length ; i1++) {
                ud_dog[i1].setText(p[i1].t.title);
                ud_dog_l[i1].setText("" + p[i1].t.net_votes);
                ques.put(ud_dog[i1], p[i1].t);
            }
        user[] ux = random.getTop4User();
        //.out.println("ux is received");
        if(ux != null)
            for(int i1 = 0 ; i1 < ux.length ; i1++) {
                ud_name[i1].setText(ux[i1].full_name);
                ud_b_l[i1].setText(ux[i1].fwers + "");
                uses.put(ud_name[i1], ux[i1]);
            }
        post[] p2 = u.u_p.getPosts();
        //System.out.println("p2 is received");
        //if(p2 == null)
            //System.out.println("p2 is null");
        if(p2 != null) {
            //System.out.println("length of p2: " + p2.length);
            for (int i1 = 0; i1 < p2.length; i1++) {
                //System.out.println(p2[i1].content);
                ud_ques[i1].setText(p2[i1].content);
                ud_nv_l[i1].setText(p2[i1].t.net_votes + "");
                ud_ta_l[i1].setText(p2[i1].t.total_ans + "");
                ques.put(ud_ques[i1], p2[i1].t);
            }
        }
        System.out.println("ending ud_init()");
    }

    @FXML
    void ud_init(user u) {
        ud_current = u;
        if(ud_current != this.u)
            image_book.setVisible(true);
        else
            image_book.setVisible(false);
        Path path2 = Paths.get(u.shadow);
        String s = replace(path2.toString());
        File file2 = new File(s);
        Image image = new Image(file2.toURI().toString());
        //System.out.println(file2.toURI().toString()+"\n"+image);
        ud_image.setImage(image);
        System.out.println("Starting ud_init");
        ud_current = u;
        ud_name_label.setText(u.full_name);
        ud_email_label.setText(u.email);
        ud_book_label.setText(u.fwers + "");
        ud_bookb_label.setText(u.fing + "");
        Hyperlink[] ud_dog = {ud_dog_hyp1, ud_dog_hyp2, ud_dog_hyp3, ud_dog_hyp4, ud_dog_hyp5, ud_dog_hyp6};
        Hyperlink[] ud_name = {ud_name_hyp1, ud_name_hyp2, ud_name_hyp3, ud_name_hyp4};
        Hyperlink[] ud_ques = {ud_ques_hyp1,  ud_ques_hyp2, ud_ques_hyp3, ud_ques_hyp4, ud_ques_hyp5};
        Label[] ud_dog_l = {ud_dog_label1, ud_dog_label2, ud_dog_label3, ud_dog_label4, ud_dog_label5, ud_dog_label6};
        Label[] ud_nv_l = {ud_nv_label1, ud_nv_label2, ud_nv_label3, ud_nv_label4, ud_nv_label5};
        Label[] ud_ta_l = {ud_ta_label1, ud_ta_label2, ud_ta_label3, ud_ta_label4, ud_ta_label5};
        Label[] ud_b_l = {ud_bookb_label1, ud_bookb_label2, ud_bookb_label3, ud_bookb_label4};

        post[] p = u.u_p.getDogearedPosts();
        //System.out.println("p is received");
        if(p != null)
            for(int i1 = 0 ; i1 < p.length ; i1++) {
                ud_dog[i1].setText(p[i1].t.title);
                ud_dog_l[i1].setText("" + p[i1].t.net_votes);
                ques.put(ud_dog[i1], p[i1].t);
            }
        user[] ux = random.getTop4User();
        //.out.println("ux is received");
        if(ux != null)
            for(int i1 = 0 ; i1 < ux.length ; i1++) {
                ud_name[i1].setText(ux[i1].full_name);
                ud_b_l[i1].setText(ux[i1].fwers + "");
                uses.put(ud_name[i1], ux[i1]);
            }
        post[] p2 = u.u_p.getPosts();
        //System.out.println("p2 is received");
        //if(p2 == null)
        //System.out.println("p2 is null");
        if(p2 != null) {
            //System.out.println("length of p2: " + p2.length);
            for (int i1 = 0; i1 < p2.length; i1++) {
                //System.out.println(p2[i1].content);
                ud_ques[i1].setText(p2[i1].content);
                ud_nv_l[i1].setText(p2[i1].t.net_votes + "");
                ud_ta_l[i1].setText(p2[i1].t.total_ans + "");
                ques.put(ud_ques[i1], p2[i1].t);
            }
        }
        System.out.println("ending ud_init()");
    }

    void df_init() throws Exception {
        /*
        try {
            Path p = Paths.get("C:\\Users\\ACER\\IdeaProjects\\tmd_mkc\\src\\smp_fx\\up_icon.png");
            Image image1 = new Image(new File(replace(p.toString())).toURI().toString(), 0, 100, false, false);
            up_image.setGraphic(new ImageView(image1));
            up_image.setLayoutX(393);
            up_image.setLayoutY(137);

            p = Paths.get("C:\\Users\\ACER\\IdeaProjects\\tmd_mkc\\src\\smp_fx\\down_icon.png");
            image1 = new Image(new File(replace(p.toString())).toURI().toString(), 0, 100, false, false);
            down_image.setGraphic(new ImageView(image1));
            down_image.setLayoutX(393);
            down_image.setLayoutY(163);
        } catch(Exception e) {
            System.out.println("error when setting Graphic: " + e);
        }
        */
        Hyperlink[] df_topic = {df_topic_hyp1, df_topic_hyp2, df_topic_hyp3, df_topic_hyp4, df_topic_hyp5, df_topic_hyp6};
        System.out.println("Starting df_init()");
        for( int i1 = 0; i1 < f.length ; i1++) {
            df_topic[i1].setText(f[i1].name);
            forums.put(df_topic[i1], f[i1]);
        }
        post p = random.getTopQues();
        //System.out.println("forum name is :" + p.t.f.name);
        if(p != null)
            df_set(p.t);
        System.out.println("ending df_init()");
    }

    void df_set(thread t) throws Exception {
        ct = t;
        df_ans_label.setVisible(true);
        df_netv.setText("" + t.net_votes);

//      System.out.println("forum name is :" + t.f.name);
        df_ans_label.setText("");
        df_ques_label.setText(t.p.content);
        df_topic_label.setText(t.f.name);
        df_topic_label1.setText(t.f.name);
        df_title_label.setText(t.title);

        post[] ans = t.getPosts();
        if(ans != null)
            for(int i1 = 0 ; i1 < ans.length ; i1++) {

                df_ans_label.setText(df_ans_label.getText() + "\n\n\n" + ans[i1].u.full_name + ":\t" + ans[i1].content);
            }
        dfSide(t.f);
    }

    void dfSide(forum f) {
        Hyperlink[] left_s_ques = {df_ques_hyp1, df_ques_hyp2, df_ques_hyp3, df_ques_hyp4, df_ques_hyp5, df_ques_hyp6};
        Label[] left_s_label = {df_nv_label1, df_nv_label2, df_nv_label3, df_nv_label4, df_nv_label5, df_nv_label6};
        thread[] tx = f.getThreads();
        if(tx != null)
            for( int i1 = 0 ; i1 < tx.length ; i1++ ) {
                left_s_ques[i1].setText(tx[i1].title);
                left_s_label[i1].setText(tx[i1].net_votes + "");
                ques.put(left_s_ques[i1], tx[i1]);
            }
    }

    void iam_init() {
        System.out.println("IAM init");
        //left sidebar
        Hyperlink[] iam_cat_hyp = {iam_cat_hyp1, iam_cat_hyp2, iam_cat_hyp3, iam_cat_hyp4, iam_cat_hyp5, iam_cat_hyp6};
        for( int i1 = 0 ; i1 < 6 ; i1++ ) {
            iam_cat_hyp[i1].setText(c[i1].name);
            categories.put(iam_cat_hyp[i1], c[i1]);
            //System.out.println(categories.get(iam_cat_hyp[i1]).name);
        }
        System.out.println("IAM init end");
    }

    void iam_mainP(place p) {
        cp = p;
        iam_review_label.setText("");
        iam_cat_label.setText(p.cat.name);
        iam_title_label.setText(p.name);
        iam_loc_label.setText(p.location);
        review[] r = p.getReviews();
        if(r == null)
            return;
        for( int i1 = 0 ; i1 < r.length ; i1++ )
            iam_review_label.setText(iam_review_label.getText() + "\n\n" + r[i1].u.full_name + ":: Rating: " + r[i1].rating + "\nReview: " + r[i1].review);
    }

    void iam_sideP(category c) {
        Hyperlink[] iam_place = {iam_place_hyp1, iam_place_hyp2, iam_place_hyp3, iam_place_hyp4, iam_place_hyp5, iam_place_hyp6};
        iam_cat_label.setText(c.name);
        place[] p = c.getPlaces();
        if (p == null)
            return;
        for (int i1 = 0; i1 < p.length; i1++) {
            iam_place[i1].setText(p[i1].name);
            places.put(iam_place[i1], p[i1]);
        }
    }

    @FXML
    void bookmark() {
        u.bookmark(ud_current);
    }

    @FXML
    void dogear() {
        u.dogear(ct);
        init_home();
    }

    @FXML
    void categoryHyper(ActionEvent e) {
        //System.out.println("category Hyperlink");
        iam_sideP(categories.get((Hyperlink) e.getSource()));
    }

    @FXML
    void forumHyper(ActionEvent e) {
        //System.out.println("forum Hyperlink: "+((Hyperlink)e.getSource()).getText());
        dfSide(forums.get((Hyperlink) e.getSource()));
    }

    @FXML
    void placeHyper(ActionEvent e) {
        iam_mainP(places.get((Hyperlink) e.getSource()));
    }

    @FXML
    void quesHyper(ActionEvent e) throws Exception {
        sM.select(df_tab);
        df_set(ques.get((Hyperlink) e.getSource()));
    }

    @FXML
    void userHyper(ActionEvent e) throws Exception {
        sM.select(ud_tab);
        ud_init(uses.get((Hyperlink) e.getSource()));
    }

    @FXML
    void threadHyper(ActionEvent e) throws Exception {
        dfSide(forums.get((Hyperlink)e.getSource()));
    }

    @FXML
    void voteDown() {
        df_netv.setText("" + (Integer.parseInt(df_netv.getText()) - 1));
        u.vote(false, ct);
    }

    @FXML
    void voteUp() {
        df_netv.setText("" + (Integer.parseInt(df_netv.getText()) + 1));
        u.vote(true, ct);
    }

    void setThread(thread t) throws Exception {
        System.out.println("Set thread started");
        df_netv.setText(t.net_votes + "");
        //System.out.println("question Post:" + t.p.content);
        df_ques_label.setText(t.p.content);
        df_topic_label.setText(t.f.name);
        df_title_label.setText(t.title);
        //        df_id_label.setText(t.thread_id + "");
        //System.out.println("setting of labels complete");
        post[] ans = t.getPosts();
        //if(ans == null)
//            /System.out.println("ans is null");
        if(ans != null) {
            df_ans_label.setText("");
            for (int i1 = 0; i1 < ans.length; i1++)
                df_ans_label.setText(df_ans_label.getText() + "\n\n\n" + ans[i1].u.full_name + ":\t" + ans[i1].content);
        }
       // System.out.println("ending setThread");
    }

    @FXML
    private void addQues() throws Exception {
        //u = new user();
        //u.user_id = 1;
        System.out.println("dasdad");
        String title = df_title_text.getText();
        String content = df_ques_text.getText();
        //String topic = ques_cb.g
        if(df_combo.getSelectionModel().isEmpty() || title.equals("") || title == null || content.equals("") || content == null) {
            error.setVisible(true);
            error.setText("Please fill all the fields properly.");
            return;
        }
        int val = df_combo.getSelectionModel().getSelectedIndex();
        thread t = u.addQues(title, content, f[val]);
        if(t == null)
            System.out.println("t returned by addQues is null");
        else
            setThread(t);
//        /System.out.println(val);
    }

    @FXML
    private void addAnswer() throws Exception {
        //u = warehouse.getUserInstance(1);
        String s = df_ans_text.getText();

        //System.out.println("thread completed");
        //setThread(t);
        //System.out.println("Back to add Ans");
        try {
            if(s == null || s.equals("")) {
                error.setText("Please enter the fields properly.");
                error.setVisible(true);
                return;
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        thread t = ct;
        System.out.println("Inside add answer: Answer is. :::   "+s+"\tThread:"+ct.title);
        u.addPost(s, t, t.f, false);
        setThread(t);
    }

    void setPlace(place p) throws Exception {
        System.out.println("Set thread started");
        iam_place_id.setText(p.place_id + "");
        //System.out.println("question Post:" + t.p.content);
        iam_cat_label.setText(p.cat.name);
        iam_title_label.setText(p.u.full_name);
        iam_loc_label.setText(p.location);
        //System.out.println("setting of labels complete");
        review[] ans = p.getReviews();
        //if(ans == null)
//            /System.out.println("ans is null");
        iam_review_label.setText("");
        if(ans != null)
            for(int i1 = 0; i1 < ans.length; i1++)
                iam_review_label.setText(iam_review_label.getText() + "\n\n\n" + ans[i1].u.full_name + "\t" + ans[i1].rating +"\n" + ans[i1].review);

        // System.out.println("ending setThread");
    }

    @FXML
    void addPlace() throws Exception {
        //u = warehouse.getUserInstance(1);
        String title = iam_title_text.getText();
        String location = iam_loc_textf.getText();
        //String topic = ques_cb.g

        if(iam_combo.getSelectionModel().isEmpty() || title.equals("") || title == null || location.equals("") || location == null) {
            error.setVisible(true);
            error.setText("Please fill all the fields properly.");
            return;
        }
        // System.out.println("addPlace bug marker1. AFTER field validation.");
        int val = iam_combo.getSelectionModel().getSelectedIndex();
        //System.out.println("Inside addPlace controlller. ID of category chosen is" + c[val].cat_id);
        u.addPlace(c[val], location, title);
    }

    @FXML
    void addReview() throws Exception {
        //u = warehouse.getUserInstance(1);
        String review = iam_review_text.getText();
        int rating = 0;
        try {
            rating = Integer.parseInt(iam_rating_text.getText());
        } catch (Exception e) {
            error.setVisible(true);
            error.setText("Please fill all the fields properly.");
            return;
        }
        //String topic = ques_cb.g
        if(review.equals("") || review == null || rating < 0 || rating > 10) {
            error.setVisible(true);
            error.setText("Please fill all the fields properly.");
            return;
        }
        u.addReview(cp, review, rating);
        iam_mainP(cp);
    }

    void setTXA(message m[]) {
        System.out.println(m.length);
        for(int i1 = 0 ; i1 < m.length ; i1++) {
            if(m[i1].receiver == u)
                txA.setText(txA.getText() + "\n" + m[i1].sender.full_name + ": " + m[i1].message);
        }
    }

    @FXML
    void logout() throws Exception {
        u.logout();
        u = null;
        m = null;
        Main.ob.start(Main.stage);
    }

    void moderator() {
        Tab mo = new Tab();
        tP.getTabs().add(4, mo);
        AnchorPane ap = new AnchorPane();
        ap.setLayoutY(0);
        ap.setLayoutY(0);
        mo.setText("Moderator");
        mo.setContent(ap);
        final TextField m_tf = new TextField();
        m_tf.setLayoutY(531);
        m_tf.setLayoutX(407);
        m_tf.setMinHeight(22);
        m_tf.setMinWidth(200);
        m_tf.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                m.moderatePosts(m_tf.getText());
            }
        });
        m_tf.setPromptText("Enter word to be blocked");
        ap.getChildren().add(m_tf);
    }

    //@FXML
    //void search() {
        //String s =
    //}

    String replace(String s) {
        String s1 = "";
        String x = "";
        for(int i1 = 0 ; i1 < s.length() ; i1++) {
            if(i1 != s.length()-1)
                x = s.substring(i1,i1+1);
            else
                x = s.substring(i1);
            if(x.equals("\\"))
                s1 += "/";
            else
                s1 += x;
        }
        //System.out.println("s after replace" + s1);
        return s1;
    }
}



