package SimulationClass;

import DataClass.DataEntry;
import DataClass.DataOut;
import SimulationClass.Client;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

public class Simulation {

    // Datos de entrada
    DataEntry dataEntry = new DataEntry();
    
    //Datos de estadisticas
    //DataStatistics dataStatistics = new DataStatistics();
    
    //Datos de cliente
    Client client = new Client();
    Client clientout = new Client();
    
    //Lista de clientes
    LinkedList <Client> [] clientList;
    
    //Datos de simulacion 
    private int   timeModeling;  //Tiempo a modelar
    private int[] serverStatus;  //Cantidad de servidores
    
    private int[] waitingLength; //lista de espera segun servidores
    private int[] eventNumber;   //numero de eventos segun servidor 
    private int[] serviceTime;   //tiempo de servicio segun servidor
    
    //Tiempo de llegada y salida segun servidor 
    private int[] arrivalTime;   //evento de llegada segun servidor
    private int[] departureTime; //evento de salida segun servidor
    private int[] nextArrivalTime;   //timpo de la siguiente llegada segun servidor
    
    // conteo segun tipo de eventos
    private int[] arrivalNumber;   //evento de llegada segun servidor
    private int[] departureNumber; //evento de salida segun servidor
    
    // Cantidad total de evento
    private int caso;
    private int extra;
    
    //Estadisticas 
    
    private int noWait;          //Cantidad de clientes que no esperan
    private int unAttendance;    //Cantidad de clientes que se van sin ser atendidos
    private double totalClient;     //Cantidad total de clientes
    private double wait;            //Cantidad total de clientes que espera
    private double probWait;        // Probabilidad de esperar
    
    //Tiempo que pasa un Cliente en el sistema
    private double[] clientSystemTime; 
    private double totalClientSystemTime;
    
    //Tiempo que pasa un Cliente en Línea de Espera
    private double[] clientWaitingTime; 
    private double totalClientWaitingTime;
    
    //Número promedio de Clientes en el sistema
    private double [] promClient;
    private double  totalPromClient;
    
    //Número promedio de Clientes en Línea de Espera
    private double [] promWaitingClient;
    private double  totalPromWaitingClient;
    
    //Porcentaje de utilización de cada servidor y general
    private double [] serverUsage;
    private double  totalserverUsage;
    
    //Tiempo promedio adicional que se trabaja después de cerrar
    private double [] promExtraClient;
    private double  totalPromExtraClient;
    
    //Porcentaje extra de Servidor
    private double[] serverExtraUsage;
    private double  totalserverExtraUsage;
    
    //tiempo de espera segun servidores
    private int[] unusedTime;    //tiempo de ocio segun servidores
    
    //Manejo del tiempo
    private int day ;
    private int laborHour;
    private int clock;

 
    public Simulation(DataEntry dataEntry) {

        this.dataEntry = dataEntry;

        this.timeModeling = 0;
        this.caso = 0;
        this.noWait = 0;
        this.totalClient = 0;
        this.wait = 0;
        this.probWait = 0;
        this.totalClientSystemTime = 0;
        this.totalClientWaitingTime = 0;
        this.totalPromWaitingClient = 0;
        this.totalserverUsage = 0;
        
        this.totalPromExtraClient = 0;
        this.totalPromClient = 0 ;
        this.totalserverExtraUsage = 0 ;
        
        this.day =1440 ;
        this.laborHour = 600;
        this.clock =0;
        
        this.clientList = new LinkedList[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            clientList[i] = new LinkedList<>();
        }
        
        this.serverStatus = new int[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.serverStatus[i] = 0;
        }
        
        this.waitingLength = new int[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.waitingLength[i] = 0;
        }
        
        this.eventNumber = new int[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.eventNumber[i] = 0; 
        }
        
        this.serviceTime = new int[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.serviceTime[i] = 0;
        }

        this.nextArrivalTime = new int[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.nextArrivalTime[i] = 0;
        }
        
        this.arrivalNumber = new int[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.arrivalNumber[i] = 0;
        }
        
        this.departureNumber = new int[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.departureNumber[i] = 0; 
        }
        
        this.arrivalTime = new int[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.arrivalTime[i] = 0; 
        }
        this.departureTime = new int[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.departureTime[i] = Integer.MAX_VALUE; 
        }
        
         this.clientSystemTime = new double[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.clientSystemTime[i] = 0; 
        }
        
        this.clientWaitingTime = new double[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.clientWaitingTime[i] = 0; 
        }
        
        this.promClient = new double[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.promClient[i] = 0; 
        }
        
        this.promWaitingClient = new double[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.promWaitingClient[i] = 0; 
        }
       
        this.serverUsage = new double[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.serverUsage[i] = 0;
        } 
        
        this.promExtraClient = new double[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.promExtraClient[i] = 0;
        } 
        this.serverExtraUsage = new double[dataEntry.getQuantityServers()];
        for (int i = 0; i < dataEntry.getQuantityServers(); i++) {
            this.promExtraClient[i] = 0;
        } 
    }
    
    public int assignmentAT(){
        int [] p = new int [5];
        p[0]=15;
        p[1]=45;
        p[2]=80;
        p[3]=90;
        p[4]=100;

        int [] v = new int [5];
        v[0]=3;
        v[1]=5;
        v[2]=7;
        v[3]=8;
        v[4]=9;


        int num = 0;
        int i = 0;
        int b=(int)(Math.random()*99);

        while(num == 0){

            if(b < (p[i])){
              num = v[i]; 

            }
            i++;   
        }
       return num; 
    }
        
    public int assignmentTS(){
        int [] p = new int [4];
        p[0]=10;
        p[1]=40;
        p[2]=65;
        p[2]=100;

        int [] v = new int [4];
        v[0]=15;
        v[1]=17;
        v[2]=20;
        p[3]=25;

        int num = 0;
        int i = 0;
        int b=(int)(Math.random()*99);
        while(num == 0){
            if(b < (p[i])){
              num = v[i];
            }
            i++;   
        }
       return num; 
    }
        

    public DataOut process() {

        DataOut dataOut = new DataOut();
        String eventName = "condicion inicial";
        int i = 0;
        DefaultTableModel eventModelTable = new DefaultTableModel(null, this.getTitles());
         eventModelTable.addRow(this.addRow(this.eventNumber[i],eventName,0,timeModeling,this.serverStatus,this.waitingLength[i],
                                            0,0,this.nextArrivalTime[i],this.serviceTime[i]
                                            )
                                );   
        
        do {
            for( i = 0; i < this.dataEntry.getQuantityServers(); i++ ){
                
                /**
                 * this.day =1440 ;
                 * this.laborHour = 600;
                 * this.clock =0;
                */
                
                //MANEJO DEL DIA 

                if(this.clock <= this.laborHour){
                    
                    if( this.arrivalTime[i] <= this.departureTime[i] ){ 
                   
                        //PROCESANDO UNA LLEGADA
                        this.eventNumber[i]= this.eventNumber[i]+1;
                        this.arrivalNumber[i] = this.arrivalNumber[i]+1;

                        eventName = "Llegada de cliente";

                        //Establecer TM = AT 
                        this.timeModeling = this.arrivalTime[i];

                        //Revisamos estados de los servidores 
                        for(int j = 0; j < this.dataEntry.getQuantityServers();j++){

                            if(this.serverStatus[j] == 0){

                                //Cambiamos estado del servido j
                                this.serverStatus[j] = 1;

                                //Generamos un tiempo de servicio para generar el tiempo de salida
                                this.serviceTime[i] = this.assignmentTS();

                                //DT = TM + TS
                                this.departureTime[i] = this.timeModeling + this.serviceTime[i];

                                //Asignando caso para poner de primero en la cola 
                                this.caso = 1;

                                //Estadisticas 
                                this.noWait++;

                                break;
                            }else {
                                this.waitingLength[i] = this.waitingLength[i]+1;
                                //Asignando caso para poner de ultimo en la cola
                                this.caso = 2; 

                               //Estadisticas
                               this.unAttendance++;
                               this.wait++;
                            } 
                        }
                        //Generamos tiempo entre llegadas
                        this.nextArrivalTime[i] = this.assignmentAT(); 

                        //actualizar clock al tiempo actual 
                        this.clock = this.clock + this.nextArrivalTime[i];

                        //Establecer AT = TM + TE  
                        this.arrivalTime[i]= (this.timeModeling + this.nextArrivalTime[i]);
                    

                        //Casos para asginar al inicio o al final
                        if (caso == 1){
                            this.clientList[i].addFirst(new Client(this.arrivalNumber[i],this.arrivalTime[i],this.serviceTime[i],this.departureTime[i]));
                        } else if  (caso == 2){
                            this.clientList[i].addLast(new Client(this.arrivalNumber[i],this.arrivalTime[i],0,0));
                        }
   
                    }else if( this.arrivalTime[i] > this.departureTime[i] ){

                        //PROCESANDO UNA SALIDA
                        this.eventNumber[i] = this.eventNumber[i]+1;
                        this.departureNumber[i] = this.departureNumber[i]+1;
                        eventName = "Salida de cliente";

                        //Establecer TM = DT
                        this.timeModeling = this.departureTime[i];
                    
                        // Sacamos el primer elemento de las lista (Cliente actual)porque se va
                        this.clientout = this.clientList[i].pollFirst();
                        
                        //Estadistiscas
                        this.clientSystemTime[i] = this.clientSystemTime[i] + (this.clientout.getClientDepartureTime()/this.departureNumber[i]);
                        this.promClient[i] = this.promClient[i] + (this.clientout.getClientDepartureTime()*this.clientout.getClientNumber()) ;
                        
                        this.serverUsage[i]= this.serverUsage[i]+ this.clientout.getClientServiceTime();
                        
                        if (this.waitingLength[i] > 0) {
                        
                        this.waitingLength[i] = this.waitingLength[i] - 1;

                            //Generamos tiempo de Salida 

                            //Generamos un tiempo de servicio para generar el tiempo de salida
                            this.serviceTime[i] = this.assignmentTS();

                            //Establecemos  DT =  TM + TS
                            this.departureTime[i] = this.timeModeling + this.serviceTime[i];

                            //Asignando TS y DT al primero que esta en la cola... el nuevo cliente que se encuentra servicio ahora
                            this.client = this.clientList[i].pollFirst();
                            this.client.setClientServiceTime(this.serviceTime[i]);
                            this.client.setClientDepartureTime(this.departureTime[i]);
                            this.clientList[i].addFirst(new Client(this.client.getClientNumber(),this.client.getClientArrivalTime(),this.client.getClientServiceTime(),this.client.getClientDepartureTime()));

                            //Estadisticas
                          
                            this.unAttendance--;
                            this.clientWaitingTime[i] = this.clientWaitingTime[i] + ((this.clientout.getClientDepartureTime()- this.client.getClientArrivalTime())/this.client.getClientNumber());

                            this.promWaitingClient[i] = this.promWaitingClient[i] + (this.clientout.getClientDepartureTime()- this.client.getClientArrivalTime());
                        } else if (this.waitingLength[i] == 0) {
                         
                            this.serverStatus[i] = 0;
                            this.departureTime[i] = Integer.MAX_VALUE;
                        }
                    }
               
                    //Impresion de simulacion

                    if ("Llegada de cliente".equals(eventName)&& this.waitingLength[i] == 0){
                        eventModelTable.addRow(this.addRow(this.eventNumber[i],eventName,this.clientList[i].peekFirst().getClientNumber(),timeModeling,
                                               this.serverStatus,this.waitingLength[i],this.clientList[i].peekFirst().getClientArrivalTime(),this.clientList[i].peekFirst().getClientDepartureTime(),
                                                this.nextArrivalTime[i],this.serviceTime[i]
                                                            )
                                               ); 

                    } else if ("Llegada de cliente".equals(eventName)){
                        eventModelTable.addRow(this.addRow(this.eventNumber[i],eventName,this.clientList[i].peekLast().getClientNumber(),timeModeling,
                                               this.serverStatus,this.waitingLength[i],this.clientList[i].peekLast().getClientArrivalTime(),this.clientList[i].peekLast().getClientDepartureTime(),
                                                this.nextArrivalTime[i],0
                                                            )
                                               );

                    } else if ("Salida de cliente".equals(eventName)){
                        eventModelTable.addRow(this.addRow(this.eventNumber[i],eventName,this.clientout.getClientNumber(),timeModeling,
                                               this.serverStatus,this.waitingLength[i],this.clientout.getClientArrivalTime(),this.clientout.getClientDepartureTime(),
                                                0,this.serviceTime[i]
                                                            )
                                               );
                    }  
                
                }else if(this.clock > this.laborHour && this.clock <= this.day  ){
                    
                    if (this.clientList[i].isEmpty() == true){
                        this.clock = Integer.MAX_VALUE; 
                    }else{
                        
                        //PROCESANDO UNA SALIDA
                        this.eventNumber[i] = this.eventNumber[i]+1;
                        this.departureNumber[i] = this.departureNumber[i]+1;
                        eventName = "Salida de cliente";

                        //Establecer TM = DT
                        this.timeModeling = this.departureTime[i];
                    
                        // Sacamos el primer elemento de las lista (Cliente actual)porque se va
                        this.clientout = this.clientList[i].pollFirst();
                        
                        //Estadistiscas
                        this.clientSystemTime[i] = this.clientSystemTime[i] + (this.clientout.getClientDepartureTime()/this.departureNumber[i]);
                        this.promClient[i] = this.promClient[i] + (this.clientout.getClientDepartureTime()*this.clientout.getClientNumber()) ;
                        
                        this.serverExtraUsage[i]= this.serverExtraUsage[i]+ this.clientout.getClientServiceTime();
                        
                        if (this.waitingLength[i] > 0) {
                        
                        this.waitingLength[i] = this.waitingLength[i] - 1;

                            //Generamos tiempo de Salida 

                            //Generamos un tiempo de servicio para generar el tiempo de salida
                            this.serviceTime[i] = this.assignmentTS();

                            //Establecemos  DT =  TM + TS
                            this.departureTime[i] = this.timeModeling + this.serviceTime[i];

                            //Asignando TS y DT al primero que esta en la cola... el nuevo cliente que se encuentra servicio ahora
                            this.client = this.clientList[i].pollFirst();
                            this.client.setClientServiceTime(this.serviceTime[i]);
                            this.client.setClientDepartureTime(this.departureTime[i]);
                            this.clientList[i].addFirst(new Client(this.client.getClientNumber(),this.client.getClientArrivalTime(),this.client.getClientServiceTime(),this.client.getClientDepartureTime()));

                            //Estadisticas
                            this.clientWaitingTime[i] = this.clientWaitingTime[i] + ((this.clientout.getClientDepartureTime()- this.client.getClientArrivalTime())/this.client.getClientNumber());

                            this.promWaitingClient[i] = this.promWaitingClient[i] + (this.clientout.getClientDepartureTime()- this.client.getClientArrivalTime());
                        
                            this.promExtraClient[i] = this.promExtraClient[i] + ((this.clientout.getClientDepartureTime()- this.client.getClientArrivalTime())/this.client.getClientNumber());
                            
                            this.unAttendance--;
                        } else if (this.waitingLength[i] == 0) {
                         
                            this.serverStatus[i] = 0;
                            this.departureTime[i] = Integer.MAX_VALUE;
                        }
                        
                        //Impresion de salida
                        eventModelTable.addRow(this.addRow(this.eventNumber[i],eventName,this.clientout.getClientNumber(),timeModeling,
                                               this.serverStatus,this.waitingLength[i],this.clientout.getClientArrivalTime(),this.clientout.getClientDepartureTime(),
                                                0,this.serviceTime[i]
                                                            )
                                               );      
                    }
                    
                }else if(this.clock > this.day ){
                    this.clock = 0;
                }
 
            } 
        }while(this.timeModeling < this.dataEntry.getTimeSimulation());  
        
        // Impresion de estadisticas
        System.out.println("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- ESTADISTICAS -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
        System.out.println("Cantidad de clientes que no esperan: " + this.noWait);
        System.out.println("Cantidad de clientes que se ven sin ser atendidos: " + this.unAttendance);
        
        for ( int j = 0; j < dataEntry.getQuantityServers(); j++) {
            this.totalClient = this.totalClient + this.arrivalTime[j];
        }
        this.probWait = (this.wait/this.totalClient) * 100;
        System.out.format("Probabilidad de esperar:%.2f",this.probWait);System.out.println(""); 
        
//PROMEDIO DE CANTIDADES
        for ( int j = 0; j < dataEntry.getQuantityServers(); j++) {
            this.totalPromClient = this.totalPromClient+this.promClient[j];
        }
        this.totalPromClient = this.totalPromClient / dataEntry.getTimeSimulation();
        System.out.format("Número promedio de Clientes en el sistema:%.2f",totalPromClient);System.out.println("");
        
        for ( int j = 0; j < dataEntry.getQuantityServers(); j++) {
            this.totalPromWaitingClient = this.totalPromWaitingClient+this.promWaitingClient[j];
        }
        this.totalPromWaitingClient = this.totalPromWaitingClient / dataEntry.getTimeSimulation();
        System.out.format("Número promedio de Clientes en Línea de Espera:%.2f",Math.abs(totalPromWaitingClient));System.out.println("");
        
        //PROMEDIO DE TIEMPOS
        for ( int j = 0; j < dataEntry.getQuantityServers(); j++) {
            this.totalClientSystemTime = this.totalClientSystemTime+this.clientSystemTime[j];
        }
        System.out.format("Tiempo promedio de los clientes en el sistema:%.2f",this.totalClientSystemTime);System.out.println(""); 
        
        for ( int j = 0; j < dataEntry.getQuantityServers(); j++) {
            this.totalClientWaitingTime = this.totalClientWaitingTime+this.clientWaitingTime[j];
        }
        System.out.format("Tiempo promedio de los clientes en linea de espera:%.2f",Math.abs(this.totalClientWaitingTime));System.out.println(""); 
        
        for ( int j = 0; j < dataEntry.getQuantityServers(); j++) {
            this.totalPromExtraClient = this.totalPromExtraClient+this.promExtraClient[j];
        }
        System.out.format("Tiempo promedio adicional que se trabaja después de cerrar:%.2f",totalPromExtraClient);System.out.println(""); 
        
    
        //PROMEDIO DE USO DEL SERVIDOR
        for ( int j = 0; j < dataEntry.getQuantityServers(); j++) {
            this.totalserverUsage = this.totalserverUsage+(this.serverUsage[j]/dataEntry.getTimeSimulation());
        }
        System.out.format("Porcentaje de utilización del sistema: %.2f",(this.totalserverUsage ));System.out.println(""); 
        
        for ( int j = 0; j < dataEntry.getQuantityServers(); j++) {
            this.totalserverExtraUsage = this.totalserverExtraUsage+(this.serverExtraUsage[j]/dataEntry.getTimeSimulation());
        }
        System.out.format("Porcentaje de utilización del sistema: %.2f",(this.totalserverUsage *100 ));System.out.println(""); 

        //COSTOS
            //SERVIDORES
        double u = ((this.totalserverUsage * dataEntry.getBusyServercost())/5);
        double t = ((this.totalserverExtraUsage * dataEntry.getExtraTimeServerCost())/5);
        
        double totalSeverCost = u+t;
        
        System.out.format("Costo Servidor tiempo Normal: %.2f",(u));System.out.println(""); 
        
        System.out.format("Costo Servidor tiempo Extra: %.2f",(t));System.out.println(""); 

        System.out.format("Costo Total del uso de Servidores: %.2f",(totalSeverCost));System.out.println(""); 
        
            //CLIENTES
        u =(dataEntry.getCostNormalOperation() * totalPromClient)/5;
        t= (totalClientWaitingTime * dataEntry.getExtraOperationCost())/5;
        double totalClientCost = (u + t);
        
        System.out.format("Costo total de Cliente en hora normal: %.2f",(u));System.out.println(""); 
        
        System.out.format("Costo total de Clientes en cola: %.2f",(t));System.out.println(""); 

        System.out.format("Costo total de  operaciones de Clientes : %.2f",(totalClientCost));System.out.println(""); 
        
            //SISTEMA
        System.out.format("Costo total del sistema: %.2f",(totalSeverCost+totalClientCost));System.out.println(""); 

        
        dataOut.setEventTable(eventModelTable);
        return dataOut;
    }

    private String[] getTitles() {
        int ntitles = 4 + this.dataEntry.getQuantityServers() + 5;
        int contTitles = 0;
        String[] titles = new String[ntitles];
        
        titles[contTitles] = "Evento N°";
        contTitles++;
        
        titles[contTitles] = "tipo de Evento";
        contTitles++;
        
        titles[contTitles] = "N° Cliente";
        contTitles++;
        
        titles[contTitles] = "TM";
        contTitles++;
        
        for (int i = 0; i < this.dataEntry.getQuantityServers(); i++, contTitles++) {
            titles[contTitles] = "SS " + (i + 1);
        }
        
        titles[contTitles] = "WL";
        contTitles++;
        
        titles[contTitles] = "AT";
        contTitles++;

        titles[contTitles] = "DT";
        contTitles++;
        
        titles[contTitles] = "TLLE";
        contTitles++;
        
        titles[contTitles] = "TS";
        contTitles++;

        return titles;
    }
       

    private Object[] addRow(int eventNumber, String eventName, int clientNumber,int timeModeling, int[] statusServers, 
                            int waitingLenght, int arrivalTime, int departureTime,int nextArrivalTime, int serviceTime) {

        ArrayList row = new ArrayList();
        
        
        row.add(eventNumber);
        row.add(eventName);
        row.add(clientNumber);
        row.add(timeModeling);
        
        for (int i = 0; i < statusServers.length; i++) {
            row.add(statusServers[i]);  
        }
        row.add(waitingLenght);
        row.add(arrivalTime);
        row.add(departureTime);
        row.add(nextArrivalTime);
        row.add(serviceTime);
        return row.toArray();
    }
}

