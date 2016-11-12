 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server2;

import server2.MSG.MSGStruct;
import java.net.Socket;

/**
 *
 * @author progr
 */
public class QStruct {
    MSGStruct msg;
    Server2.Clientconnection cli;

    public QStruct(MSGStruct msg, Server2.Clientconnection cli) {
        this.msg=msg;
        this.cli=cli;
    }
    

    public MSGStruct getMsg() {
        return msg;
    }

    public Server2.Clientconnection getcli(){
        return cli;
    }
    
}
