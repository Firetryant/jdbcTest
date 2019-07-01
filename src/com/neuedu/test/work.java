package com.neuedu.test;

public class work {
    //混元霹雳手 08:09:55
           // package com.neuedu.pojo;

    public class Tests {
        private int id;
        private  String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Tests(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Tests() {
        }

        @Override
        public String toString() {
            return "Tests{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

   // 混元霹雳手 08:10:06
          //  package com.neuedu.test;

import com.neuedu.pojo.Tests;
import com.neuedu.util.JdbcTest;

import java.util.List;

    public class Test {
        public static void main(String[] args) {
//        Tests tests= new Tests(20,"qwer");
//        JdbcTest.executeUpdate("insert into Tests(id,name) values(?,?)",tests.getId(),tests.getName());
            JdbcTest.selectMethod();
            List<Tests> ts= JdbcTest.selectMethod();
            for (Tests s:ts
            ) {
                System.out.println(s);
            }
        }
    }

    //混元霹雳手 08:10:16
           // package com.neuedu.util;

import com.mysql.jdbc.Driver;
import com.neuedu.pojo.Tests;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//增删改查  2重复代码  3 结果存储  拿到结果  怎么存储


    public class JdbcTest {
        public static final String  URL ="jdbc:mysql://127.0.0.1:3306/test1?useUnicode=true&characterEncoding=utf8";
        public static final String  user="root";
        public static final String  prd="root";

        static{
            //加载驱动
            try {
                new Driver();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//    动态参数
//    public static void move(int... ints){
//       for(int i=0;i<ints.length;i++){
//           System.out.println(i);
//       }
//
//    }
//    public static void main(String[] args) {
//        move(1,2,3,4);
//        insertMethod();
//        selectMethod();
//        updateMethod();
//        deleteMethod();
//        insertMethod("insert into Tests(id,name) values(?,?)",13,"封装测试");
//   }
//    查询的方法

        public static List<Tests>  selectMethod(){
            List<Tests> ts= new ArrayList<>();
            Connection  conn=null;
            PreparedStatement pstm=null;
            ResultSet rs=null;
            try {
                conn=DriverManager.getConnection(URL,user,prd);
                String sql ="select * from Tests";
                // where name=?";
                pstm=conn.prepareStatement(sql);
//           pstm.setString(1,"qqqq");
                rs=pstm.executeQuery();
                //     创建查询窗口
//            ?  占位
//            statement  和 PreparedStatement 的区别
//          state=conn.createStatement();
//  如果条件是字符串   要加引号
//            sql  注入   加入  横成立的  or   1=1
//            sql    相对安全  （相对）
//            String sql = "select * from Tests";
//            rs= state.executeQuery(sql);

                while(rs.next()){
//               int a=rs.getInt(1);
////               String a=rs.getString("name");
////               int a=rs.getInt("id");
//               System.out.println(a);
                    int id=rs.getInt("id");
                    String name =rs.getString("name");
                    Tests tests = new Tests(id,name);
                    ts.add(tests);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
//            try {
//                rs.close();
//                state.close();
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
                close(rs,pstm,conn);
            }
            return  ts;
        }

        //    动态参数一定要放在参数列表的最后位置
//    插入的方法   英文     中文
//    对增删 封装的一个方法  对任意数据进行  操作  ------对数据库操作的根据类
        public static int executeUpdate(String sql,Object... objs){
            Connection  conn=null;
            PreparedStatement pstm =null;
            int result=0;
            try {
                conn=DriverManager.getConnection(URL,user,prd);
                pstm=conn.prepareStatement(sql);
                if(objs!=null){
                    for(int i=0;i<objs.length;i++){
                        pstm.setObject(i+1,objs[i]);

                    }
                }
                result= pstm.executeUpdate();
//            System.out.println(result);

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                close(null,pstm,conn);
            }
            return  result;
        }
//    public static void insertMethod(){
//        Connection  conn=null;
//        Statement state=null;
     /*   try {
            conn=DriverManager.getConnection(URL,user,prd);
            state=conn.createStatement();
            String sql = "insert into Tests(name) values('中文')";
            int result=state.executeUpdate(sql);
            System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
           close(null,state,conn);
        }*/

//    }
//    改的方法
//    public static void  updateMethod(){
//        Connection conn= null;
//        Statement state=null;


        /* try {
             conn = DriverManager.getConnection(URL,user,prd);
             state =conn.createStatement();
             String sql="update  Tests set   name='b' where id=1  ";
             int result=state.executeUpdate(sql);
             System.out.println(result);
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
            close(null,state,conn);

         }*/
//    }
//    删的方法
//public static void  deleteMethod(){
//    Connection conn= null;
//    PreparedStatement pstm=null;
   /* try {
        conn = DriverManager.getConnection(URL,user,prd);
        String sql="delete from  Tests where  id=4";
        pstm=conn.prepareStatement(sql);
//        state =conn.createStatement();
//        String sql="delete from  Tests where  id=4";
//        int result=state.executeUpdate(sql);
//        System.out.println(result);
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        close(null,pstm,conn);
    }*/
//}
//关闭的方法
        public  static void close( ResultSet rs,PreparedStatement pstm,Connection conn){
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pstm!=null){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//修改  删除
//当前方法  不足   缺点

}
