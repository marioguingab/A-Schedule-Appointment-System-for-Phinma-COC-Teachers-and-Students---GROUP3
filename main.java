import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class main {
    static Scanner sc = new Scanner(System.in);

    static String[][] teachers = {
        {"cruz@phinmaed.com","teacher123","Anderson Cruz"},
        {"santos@phinmaed.com","teacher123","Brian Santos"},
        {"reyes@phinmaed.com","teacher123","Carlos Reyes"}
    };

    static String[][] students = {
        {"02-2526-000459","mario123","Mario Guingab"},
        {"02-2526-012013","marlou123","Marlou Morales"}
    };

    static String[] times = {"7AM","8AM","9AM","10AM","11AM","12PM","1PM","2PM","3PM","4PM","5PM","6PM","7PM"};

    static class Appointment {
        String studentName, teacherName, date, time, status, room;
        Appointment(String s, String t, String d, String ti, String st){
            studentName = s; teacherName = t; date = d; time = ti; status = st; room="";
        }
    }

    static ArrayList<Appointment> appointments = new ArrayList<>();
    static String currentStudent = "";
    static String currentTeacher = "";

    public static void main(String[] args){
        while(true){
            System.out.println("\n");
            System.out.println("=".repeat(30));
            System.out.println("LOGIN PANEL - PHINMA COC");
            System.out.println("=".repeat(30));
            System.out.println("1. Teacher Login Panel");
            System.out.println("2. Student Login Panel");
            System.out.println("0. Exit Program");
            System.out.println("=".repeat(30));
            System.out.print("[?] Choose: ");
            int choice = safeInt(0,2);
            switch(choice){
                case 1: teacherLogin(); break;
                case 2: studentLogin(); break;
                case 0: return;
            }
        }
    }

    static int safeInt(int min, int max){
        while(true){
            try{
                int n = Integer.parseInt(sc.nextLine());
                if(n>=min && n<=max) return n;
            }catch(Exception e){}
            System.out.print("[?] Choose: ");
        }
    }

    static ArrayList<String> generateNextDates(int daysAhead){
        ArrayList<String> list = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy (EEE)");
        for(int i=0;i<daysAhead;i++){
            LocalDate date = today.plusDays(i);
            list.add(date.format(formatter));
        }
        return list;
    }

    static void teacherLogin(){
        System.out.print("[?] Enter your PHINMAED: "); String g = sc.nextLine();
        System.out.print("[?] Enter Password: "); String p = sc.nextLine();
        for(String[] t : teachers){
            if(t[0].equals(g) && t[1].equals(p)){
                currentTeacher = t[2];
                teacherDashboard();
                return;
            }
        }
        System.out.println("[X] Invalid Phinmaed or Password!");
    }

    static void teacherDashboard(){
        while(true){
            int approved=0,pending=0,Declined=0;
            for(Appointment a: appointments){
                if(a.teacherName.equals(currentTeacher)){
                    switch(a.status){
                        case "Approved": approved++; break;
                        case "Pending": pending++; break;
                        case "Declined": Declined++; break;
                    }
                }
            }
            System.out.println("\n");
            System.out.println("=".repeat(30));
            System.out.println("TEACHER DASHBOARD PANEL");
            System.out.println("=".repeat(30));
            System.out.println("[/] Welcome Teacher: "+currentTeacher);
            System.out.println("=".repeat(30));
            System.out.println("1. Approved ("+approved+")");
            System.out.println("2. Pending ("+pending+")");
            System.out.println("3. Declined ("+Declined+")");
            System.out.println("=".repeat(30));
            System.out.println("1. View Appointments");
            System.out.println("2. Approve Appointment");
            System.out.println("3. Decline Appointment");
            System.out.println("4. Change Appointment");
            System.out.println("0. Exit");
            System.out.println("=".repeat(30));
            System.out.print("[?] Choose: ");
            int choice = safeInt(0,4);
            switch(choice){
                case 1: teacherViewAppointments(); break;
                case 2: approveAppointment(); break;
                case 3: unapproveAppointment(); break;
                case 4: changeAppointment(); break;
                case 0: return;
            }
        }
    }

    static void teacherViewAppointments(){
        while(true){
            int approved=0,pending=0,Declined=0;
            for(Appointment a: appointments){
                if(a.teacherName.equals(currentTeacher)){
                    switch(a.status){
                        case "Approved": approved++; break;
                        case "Pending": pending++; break;
                        case "Declined": Declined++; break;
                    }
                }
            }
            System.out.println("\n");
            System.out.println("=".repeat(30));
            System.out.println("SELECT CATEGORY TO VIEW:");
            System.out.println("=".repeat(30));
            System.out.println("1. Approved ("+approved+")");
            System.out.println("2. Pending ("+pending+")");
            System.out.println("3. Declined ("+Declined+")");
            System.out.println("0. Back");
            System.out.println("=".repeat(30));
            System.out.print("[?] Choose: ");
            int statusChoice = safeInt(0,3);
            if(statusChoice==0) return;
            String status = statusChoice==1?"Approved":statusChoice==2?"Pending":"Declined";
            ArrayList<String> dates = generateNextDates(14);
            while(true){
                System.out.println("\n");
                System.out.println("=".repeat(30));
                System.out.println("SELECT DATE TO VIEW:");
                System.out.println("=".repeat(30));
                for(int i=0;i<dates.size();i++){
                    int count=0;
                    for(Appointment a:appointments){
                        if(a.teacherName.equals(currentTeacher) && a.status.equals(status) && a.date.equals(dates.get(i))) count++;
                    }
                    System.out.println((i+1)+". "+dates.get(i)+" ("+count+")");
                }
                System.out.println("0. Back");
                System.out.println("=".repeat(30));
                System.out.print("[?] Choose: ");
                int dChoice = safeInt(0,dates.size());
                if(dChoice==0) break;
                String chosenDate = dates.get(dChoice-1);
                ArrayList<Appointment> dayApps = new ArrayList<>();
                for(int i=appointments.size()-1;i>=0;i--){
                    Appointment a = appointments.get(i);
                    if(a.teacherName.equals(currentTeacher) && a.status.equals(status) && a.date.equals(chosenDate))
                        dayApps.add(a);
                }
                if(dayApps.isEmpty()){ System.out.println("[X] No appointments found."); break; }
                System.out.println("\n");
                System.out.println("=".repeat(30));
                System.out.println("Appointments on "+chosenDate+":");
                System.out.println("=".repeat(30));
                int idx=1;
                System.out.println("=".repeat(30));
                for(Appointment a: dayApps){
                    System.out.println(idx+". "+a.studentName+" | "+a.time+" | Status: "+a.status+" | Room: "+(a.room.equals("")?"N/A":a.room));
                    idx++;
                }
                System.out.println("=".repeat(30));
                break;
            }
        }
    }

    static void approveAppointment(){
        ArrayList<String> dates = generateNextDates(14);
        while(true){
            ArrayList<String> validDates = new ArrayList<>();
            for(String d : dates){
                int count = 0;
                for(Appointment ap : appointments){
                    if(ap.teacherName.equals(currentTeacher) &&
                       ap.status.equals("Pending") &&
                       ap.date.equals(d)){
                        count++;
                    }
                }
                if(count > 0){
                    validDates.add(d);
                }
            }
            if(validDates.isEmpty()){
                System.out.println("[X] No pending appointments found.");
                return;
            }
            System.out.println("\n");
            System.out.println("=".repeat(30));
            System.out.println("SELECT DATE TO PROCEED:");
            System.out.println("=".repeat(30));
            for(int i=0;i<validDates.size();i++){
                int count=0;
                for(Appointment a:appointments){
                    if(a.teacherName.equals(currentTeacher) && a.status.equals("Pending") && a.date.equals(validDates.get(i))) count++;
                }
                System.out.println((i+1)+". "+validDates.get(i)+" ("+count+")");
            }
            System.out.println("0. Back");
            System.out.println("=".repeat(30));
            System.out.print("[?] Choose: ");
            int dChoice = safeInt(0, validDates.size());
            if(dChoice==0) return;
            String chosenDate = validDates.get(dChoice - 1);
            ArrayList<Appointment> pendingList = new ArrayList<>();
            for(Appointment ap : appointments){
                if(ap.teacherName.equals(currentTeacher) &&
                   ap.status.equals("Pending") &&
                   ap.date.equals(chosenDate)){
                    pendingList.add(ap);
                }
            }
            while(true){
                System.out.println("\n");
                System.out.println("=".repeat(30));
                System.out.println("Pending Appointments on " + chosenDate + ":");
                System.out.println("=".repeat(30));
                for(int i=0;i<pendingList.size();i++){
                    Appointment ap = pendingList.get(i);
                    System.out.println((i+1)+". "+ap.studentName+" | "+ap.time);
                }
                System.out.println("0. Back");
                System.out.println("=".repeat(30));
                System.out.println("[?] Choose Appointment to Approve: ");
                int choice = safeInt(0,pendingList.size());
                if(choice==0) break;
                Appointment selected = pendingList.get(choice-1);
                String room;
                while(true){
                    System.out.print("[?] Enter Appointment Room: ");
                    room = sc.nextLine().trim();
                    if(!room.isEmpty()){
                        break;
                    }
                }
                while(true){
                    System.out.print("[?] Confirm approve? (Y/N): ");
                    String confirm = sc.nextLine().trim().toLowerCase();
                    if(confirm.equals("y")){
                        selected.status = "Approved";
                        selected.room = room;
                        System.out.println("[/] Appointment approved successfully!");
                        return;
                    } else if(confirm.equals("n")){
                        System.out.println("[X] Appointment Request Cancelled.");
                        return;
                    }
                }
            }
        }
    }

    static void unapproveAppointment(){
    ArrayList<String> dates = generateNextDates(14);

    while(true){
        ArrayList<String> validDates = new ArrayList<>();

        for(String d : dates){
            int count = 0;
            for(Appointment ap : appointments){
                if(ap.teacherName.equals(currentTeacher) &&
                   (ap.status.equals("Pending") || ap.status.equals("Approved")) &&
                   ap.date.equals(d)){
                    count++;
                }
            }
            if(count > 0){
                validDates.add(d);
            }
        }

        if(validDates.isEmpty()){
            System.out.println("[X] No appointments found.");
            return;
        }

        System.out.println("\n");
        System.out.println("=".repeat(30));
        System.out.println("SELECT DATE TO PROCEED:");
        System.out.println("=".repeat(30));

        for(int i=0;i<validDates.size();i++){
            int count=0;
            for(Appointment a:appointments){
                if(a.teacherName.equals(currentTeacher) &&
                   (a.status.equals("Pending") || a.status.equals("Approved")) &&
                   a.date.equals(validDates.get(i))) count++;
            }
            System.out.println((i+1)+". "+validDates.get(i)+" ("+count+")");
        }

        System.out.println("0. Back");
        System.out.println("=".repeat(30));
        System.out.print("[?] Choose: ");
        int dChoice = safeInt(0, validDates.size());

        if(dChoice==0) return;

        String chosenDate = validDates.get(dChoice - 1);

        ArrayList<Appointment> list = new ArrayList<>();

        for(Appointment ap : appointments){
            if(ap.teacherName.equals(currentTeacher) &&
               (ap.status.equals("Pending") || ap.status.equals("Approved")) &&
               ap.date.equals(chosenDate)){
                list.add(ap);
            }
        }

        while(true){
            System.out.println("\n");
            System.out.println("=".repeat(30));
            System.out.println("Appointments on " + chosenDate + ":");
            System.out.println("=".repeat(30));

            for(int i=0;i<list.size();i++){
                Appointment ap = list.get(i);
                System.out.println((i+1)+". "+ap.studentName+" | "+ap.time+" | Status: "+ap.status);
            }

            System.out.println("0. Back");
            System.out.println("=".repeat(30));
            System.out.print("[?] Choose Appointment to Unapprove: ");

            int choice = safeInt(0,list.size());

            if(choice==0) break;

            Appointment selected = list.get(choice-1);

            while(true){
                System.out.print("[?] Confirm decline? (Y/N): ");
                String confirm = sc.nextLine().trim().toLowerCase();

                if(confirm.equals("y")){
                    selected.status = "Declined";
                    selected.room = "N/A"; 
                    System.out.println("[/] Appointment Declined successfully!");
                    return;
                } 
                else if(confirm.equals("n")){
                    System.out.println("[X] Decline request cancelled.");
                    return;
                }
             }
          }
       }
    }

    static void changeAppointment(){
    ArrayList<String> dates = generateNextDates(14);

    while(true){
        int approved=0,pending=0,Declined=0;
        for(Appointment a: appointments){
            if(a.teacherName.equals(currentTeacher)){
                switch(a.status){
                    case "Approved": approved++; break;
                    case "Pending": pending++; break;
                    case "Declined": Declined++; break;
                }
            }
        }
        System.out.println("\n");
        System.out.println("=".repeat(30));
        System.out.println("CHANGE APPOINTMENT PANEL");
        System.out.println("=".repeat(30));
        System.out.println("1. Approved ("+approved+")");
        System.out.println("2. Pending ("+pending+")");
        System.out.println("3. Declined ("+Declined+")");
        System.out.println("0. Back");
        System.out.println("=".repeat(30));
        System.out.print("[?] Choose Status: ");

        int sChoice = safeInt(0,3);
        if(sChoice==0) return;

        String status = sChoice==1?"Pending":sChoice==2?"Approved":"Declined";

        ArrayList<String> validDates = new ArrayList<>();

        // Get dates with appointments
        for(String d:dates){
            int count=0;
            for(Appointment a:appointments){
                if(a.teacherName.equals(currentTeacher) &&
                   a.status.equals(status) &&
                   a.date.equals(d)){
                    count++;
                }
            }
            if(count>0) validDates.add(d);
        }

        if(validDates.isEmpty()){
            System.out.println("[X] No appointments found.");
            continue;
        }

        while(true){
            System.out.println("\n");
            System.out.println("=".repeat(30));
            System.out.println("SELECT DATE");
            System.out.println("=".repeat(30));

            for(int i=0;i<validDates.size();i++){
                int count=0;
                for(Appointment a:appointments){
                    if(a.teacherName.equals(currentTeacher) &&
                       a.status.equals(status) &&
                       a.date.equals(validDates.get(i))) count++;
                }
                System.out.println((i+1)+". "+validDates.get(i)+" ("+count+")");
            }

            System.out.println("0. Back");
            System.out.println("=".repeat(30));
            System.out.print("[?] Choose: ");

            int dChoice = safeInt(0, validDates.size());
            if(dChoice==0) break;

            String chosenDate = validDates.get(dChoice-1);

            ArrayList<Appointment> list = new ArrayList<>();

            for(Appointment a:appointments){
                if(a.teacherName.equals(currentTeacher) &&
                   a.status.equals(status) &&
                   a.date.equals(chosenDate)){
                    list.add(a);
                }
            }

            if(list.isEmpty()){
                System.out.println("[X] No appointments found.");
                continue;
            }

            while(true){
                System.out.println("\n");
                System.out.println("=".repeat(30));
                System.out.println("APPOINTMENTS ON "+chosenDate);
                System.out.println("=".repeat(30));

                for(int i=0;i<list.size();i++){
                    Appointment a = list.get(i);
                    System.out.println((i+1)+". "+a.studentName+" | "+a.time+" | Status: "+a.status+" | Room: "+(a.room.equals("")?"N/A":a.room));
                }

                System.out.println("0. Back");
                System.out.println("=".repeat(30));
                System.out.print("[?] Choose Appointment: ");

                int appChoice = safeInt(0, list.size());
                if(appChoice==0) break;

                Appointment selected = list.get(appChoice-1);

                System.out.println("\n");
                System.out.println("=".repeat(30));
                System.out.println("CHANGE STATUS");
                System.out.println("=".repeat(30));
                System.out.println("1. Pending");
                System.out.println("2. Approved");
                System.out.println("3. Declined");
                System.out.println("0. Back");
                System.out.println("=".repeat(30));
                System.out.print("[?] Choose: ");

                int newStatusChoice = safeInt(0,3);
                if(newStatusChoice==0) continue;

                String newStatus = newStatusChoice==1?"Pending":newStatusChoice==2?"Approved":"Declined";

                String newRoom = selected.room;

                // ✅ If Approved → require room
                if(newStatus.equals("Approved")){
                    while(true){
                        System.out.print("[?] Enter Room: ");
                        newRoom = sc.nextLine().trim();
                        if(!newRoom.isEmpty()) break;
                    }
                }
                
                if(newStatus.equals("Declined") || newStatus.equals("Pending")){
                    newRoom = "N/A";
                }

                while(true){
                    System.out.print("[?] Confirm change to "+newStatus+"? (Y/N): ");
                    String confirm = sc.nextLine().trim().toLowerCase();

                    if(confirm.equals("y")){
                        selected.status = newStatus;
                        selected.room = newRoom;
                        System.out.println("[/] Appointment updated successfully!");
                        break;
                    }
                    else if(confirm.equals("n")){
                        System.out.println("[X] Change cancelled.");
                        break;
                    }
                  }
             }
           }
       }
    }
    // ---------------- STUDENT FUNCTIONS ----------------
    static void studentLogin(){
        System.out.print("[?] Enter ID: "); String id = sc.nextLine();
    System.out.print("[?] Enter Password: "); String p = sc.nextLine();
        for(String[] s : students){
            if(s[0].equals(id) && s[1].equals(p)){
                currentStudent = s[2];
                studentDashboard();
                return;
            }
        }
        System.out.println("[X} Invalid ID or Password!");
    }

    static void studentDashboard(){
        while(true){
            int approved=0,pending=0,Declined=0;
            for(Appointment a:appointments){
                if(a.studentName.equals(currentStudent)){
                    switch(a.status){
                        case "Approved": approved++; break;
                        case "Pending": pending++; break;
                        case "Declined": Declined++; break;
                    }
                }
            }
            System.out.println("\n");
            System.out.println("=".repeat(30));
            System.out.println("STUDENT DASHBOARD PANEL");
            System.out.println("=".repeat(30));
            System.out.println("[/] Welcome Student: "+currentStudent);
            System.out.println("=".repeat(30));
            System.out.println("1. Approved ("+approved+")");
            System.out.println("2. Pending ("+pending+")");
            System.out.println("3. Declined ("+Declined+")");
            System.out.println("=".repeat(30));
            System.out.println("1. View Available Teachers");
            System.out.println("2. View Appointments");
            System.out.println("3. Book Appointment");
            System.out.println("4. Update/Cancel Appointment");
            System.out.println("0. Exit");
            System.out.println("=".repeat(30));
            System.out.print("[?] Choose: ");
            int choice = safeInt(0,4);
            switch(choice){
                case 1: viewTeachers(); break;
                case 2: viewStudentAppointments(); break;
                case 3: bookAppointment(); break;
                case 4: updateCancel(); break;
                case 0: return;
            }
        }
    }

    static void viewTeachers(){
        ArrayList<String> teacherList = new ArrayList<>();
        for(String[] t: teachers) teacherList.add(t[2]);
        Collections.sort(teacherList);
        System.out.println("\n");
        System.out.println("=".repeat(30));
        System.out.println("AVAILABLE TEACHERS:");
        System.out.println("=".repeat(30));
        int count = 1;
        for(String t: teacherList) {
            System.out.println(count + ". " +t);
            count++;
        }
        System.out.println("=".repeat(30));
    }

    static void viewStudentAppointments(){
        while(true){
            int approved=0,pending=0,Declined=0;
            for(Appointment a:appointments){
                if(a.studentName.equals(currentStudent)){
                    switch(a.status){
                        case "Approved": approved++; break;
                        case "Pending": pending++; break;
                        case "Declined": Declined++; break;
                    }
                }
            }
            System.out.println("\n");
            System.out.println("=".repeat(30));
            System.out.println("CHOOSE CATEGORY TO PROCEED:");
            System.out.println("=".repeat(30));
            System.out.println("1. Approved ("+approved+")");
            System.out.println("2. Pending ("+pending+")");
            System.out.println("3. Declined ("+Declined+")");
            System.out.println("0. Back");
            System.out.println("=".repeat(30));
            System.out.print("[?] Choose: ");
            int statusChoice = safeInt(0,3);
            if(statusChoice==0) return;
            String status = statusChoice==1?"Approved":statusChoice==2?"Pending":"Declined";
            ArrayList<String> dates = generateNextDates(14);
            while(true){
                System.out.println("\n");
                System.out.println("=".repeat(30));
                System.out.println("SELECT DATE TO VIEW: ");
                System.out.println("=".repeat(30));
                for(int i=0;i<dates.size();i++){
                    int count=0;
                    for(Appointment a:appointments){
                        if(a.studentName.equals(currentStudent) && a.status.equals(status) && a.date.equals(dates.get(i))) count++;
                    }
                    System.out.println((i+1)+". "+dates.get(i)+" ("+count+")");
                }
                System.out.println("0. Back");
                System.out.println("=".repeat(30));
                System.out.print("Choose: ");
                int dChoice = safeInt(0,dates.size());
                if(dChoice==0) break;
                String chosenDate = dates.get(dChoice-1);
                ArrayList<Appointment> dayApps = new ArrayList<>();
                for(int i=appointments.size()-1;i>=0;i--){
                    Appointment a = appointments.get(i);
                    if(a.studentName.equals(currentStudent) && a.status.equals(status) && a.date.equals(chosenDate))
                        dayApps.add(a);
                }
                if(dayApps.isEmpty()){ System.out.println("[X] No appointments found."); break; }
                System.out.println("\n");
                System.out.println("=".repeat(30));
                System.out.println("Appointments on "+chosenDate+":");
                System.out.println("=".repeat(30));
                int idx=1;
                for(Appointment a: dayApps){
                    System.out.println(idx+". "+a.teacherName+" | "+a.time+" | Status: "+a.status+" | Room: "+(a.room.equals("")?"N/A":a.room));
                    idx++;
                }
                System.out.println("=".repeat(30));
                break;
            }
        }
    }

    static void bookAppointment(){
        ArrayList<String> teacherList = new ArrayList<>();
        for(String[] t: teachers) teacherList.add(t[2]);
        Collections.sort(teacherList);
        while(true){
            System.out.println("\n");
            System.out.println("=".repeat(30));
            System.out.println("SELECT TEACHER:");
            System.out.println("=".repeat(30));
            for(int i=0;i<teacherList.size();i++) System.out.println((i+1)+". "+teacherList.get(i));
            System.out.println("0. Back");
            System.out.println("=".repeat(30));
            System.out.print("[?] Choose: ");
            int tChoice = safeInt(0, teacherList.size());
            if(tChoice==0) return;
            String chosenTeacher = teacherList.get(tChoice-1);

            ArrayList<String> dates = generateNextDates(14);
            while(true){
                System.out.println("\n");
                System.out.println("=".repeat(30));
                System.out.println("SELECT DATE:");
                System.out.println("=".repeat(30));
                for(int i=0;i<dates.size();i++) System.out.println((i+1)+". "+dates.get(i));
                System.out.println("0. Back");
                System.out.println("=".repeat(30));
                System.out.print("Choose: ");
                int dChoice = safeInt(0, dates.size());
                if(dChoice==0) break;
                String chosenDate = dates.get(dChoice-1);

                while(true){
                    System.out.println("\n");
                    System.out.println("=".repeat(30));
                    System.out.println("SELECT TIME:");
                    System.out.println("=".repeat(30));
                    for(int i=0;i<times.length;i++) System.out.println((i+1)+". "+times[i]);
                    System.out.println("0. Back");
                    System.out.println("=".repeat(30));
                    System.out.print("Choose: ");
                    int timeChoice = safeInt(0, times.length);
                    if(timeChoice==0) break;
                    String chosenTime = times[timeChoice-1];

                    boolean alreadyBooked = false;
                    for(Appointment a : appointments){
                        if(a.studentName.equals(currentStudent) &&
                            a.teacherName.equals(chosenTeacher) &&
                            a.date.equals(chosenDate) &&
                            a.time.equals(chosenTime)){
                            alreadyBooked = true;
                            break;
                        }
                    }

                    if(alreadyBooked){
                        System.out.println("[X} You already booked this.");
                        continue;
                    }

                    while(true){
                        System.out.print("[?] Confirm booking "+chosenTeacher+" on "+chosenDate+" at "+chosenTime+"? (Y/N): ");
                        String c = sc.nextLine().trim().toLowerCase();
                        if(c.equals("y")){
                            appointments.add(new Appointment(currentStudent, chosenTeacher, chosenDate, chosenTime, "Pending"));
                            System.out.println("[/] Appointment booked successfully!");
                            return;
                        } else if(c.equals("n")){
                            System.out.println("[X] Booking Request Cancelled.");
                            return;
                        }
                    }
                }
            }
        }
    }
    static void updateCancel(){

    actionLoop:
    while(true){
        System.out.println("\n");
        System.out.println("=".repeat(30));
        System.out.println("1. Update Appointment");
        System.out.println("2. Cancel Appointment");
        System.out.println("0. Back");
        System.out.println("=".repeat(30));
        System.out.print("[?] Choose: ");
        int choice = safeInt(0,2);

        if(choice==0) return;

        String action = choice==1?"Update":"Cancel";

        statusLoop:
        while(true){
            int approved=0,pending=0,Declined=0;
            for(Appointment a:appointments){
                if(a.studentName.equals(currentStudent)){
                    switch(a.status){
                        case "Approved": approved++; break;
                        case "Pending": pending++; break;
                        case "Declined": Declined++; break;
                    }
                }
            }
            System.out.println("\n");
            System.out.println("=".repeat(30));
            System.out.println("SELECT STATUS TO PROCEED:");
            System.out.println("=".repeat(30));
            System.out.println("1. Approved ("+approved+")");
            System.out.println("2. Pending ("+pending+")");
            System.out.println("0. Back");
            System.out.println("=".repeat(30));
            System.out.print("Choose: ");
            int sChoice = safeInt(0,2);

            if(sChoice==0) continue actionLoop;

            String status = sChoice==1?"Approved":"Pending";
            ArrayList<String> dates = generateNextDates(14);

            dateLoop:
            while(true){

                System.out.println("\n");
                System.out.println("=".repeat(30));
                System.out.println("SELECT DATE (" + status + "):");
                System.out.println("=".repeat(30));

                for(int i=0;i<dates.size();i++){
                    int count=0;
                    for(Appointment a:appointments){
                        if(a.studentName.equals(currentStudent)
                                && a.status.equals(status)
                                && a.date.equals(dates.get(i))) count++;
                    }
                    System.out.println((i+1)+". "+dates.get(i)+" ("+count+")");
                }

                System.out.println("0. Back");
                System.out.println("=".repeat(30));
                System.out.print("[?] Choose: ");
                int dChoice = safeInt(0, dates.size());

                if(dChoice==0) continue statusLoop;

                String chosenDate = dates.get(dChoice-1);
                ArrayList<Appointment> list = new ArrayList<>();

                for(int i=appointments.size()-1;i>=0;i--){
                    Appointment a = appointments.get(i);
                    if(a.studentName.equals(currentStudent)
                            && a.status.equals(status)
                            && a.date.equals(chosenDate)){
                        list.add(a);
                    }
                }

                if(list.isEmpty()){
                    System.out.println("No appointments found.");
                    continue;
                }

                appointmentLoop:
                while(true){

                    System.out.println("\n");
                    System.out.println("=".repeat(30));
                    System.out.println("Appointments on " + chosenDate + ":");
                    System.out.println("=".repeat(30));

                    for(int i=0;i<list.size();i++){
                        Appointment a = list.get(i);
                        System.out.println((i+1)+". "+a.teacherName+" | "+a.time
                                +" | Status: "+a.status
                                +" | Room: "+(a.room.equals("")?"N/A":a.room));
                    }

                    System.out.println("0. Back");
                    System.out.println("=".repeat(30));
                    System.out.print("[?] Choose: ");
                    int pick = safeInt(0, list.size());

                    if(pick==0) continue dateLoop;

                    Appointment selected = list.get(pick-1);

                    if(action.equals("Cancel")){

                        while(true){
                            System.out.print("[?] Confirm cancel? (Y/N): ");
                            String c = sc.nextLine().trim().toLowerCase();

                            if(c.equals("y")){
                                appointments.remove(selected);
                                System.out.println("[/] Cancelled successfully!");
                                continue actionLoop;
                            } else if(c.equals("n")){
                                System.out.println("[X] Cancel request cancelled.");
                                break;
                            }
                        }
                        break;

                    } else {

                        ArrayList<String> newDates = generateNextDates(14);

                        updateDateLoop:
                        while(true){

                            System.out.println("\n");
                            System.out.println("=".repeat(30));
                            System.out.println("SELECT NEW DATE:");
                            System.out.println("=".repeat(30));

                            for(int i=0;i<newDates.size();i++)
                                System.out.println((i+1)+". "+newDates.get(i));

                            System.out.println("0. Back");
                            System.out.println("=".repeat(30));
                            System.out.print("[?] Choose: ");
                            int nd = safeInt(0, newDates.size());

                            if(nd==0) continue dateLoop;

                            String newDate = newDates.get(nd-1);

                            timeLoop:
                            while(true){

                                System.out.println("\n");
                                System.out.println("=".repeat(30));
                                System.out.println("SELECT NEW TIME:");
                                System.out.println("=".repeat(30));

                                for(int i=0;i<times.length;i++)
                                    System.out.println((i+1)+". "+times[i]);

                                System.out.println("0. Back");
                                System.out.println("=".repeat(30));
                                System.out.print("[/] Choose: ");
                                int nt = safeInt(0, times.length);

                                if(nt==0) continue updateDateLoop;

                                String newTime = times[nt-1];

                                boolean exists = false;
                                for(Appointment a:appointments){
                                    if(a.teacherName.equals(selected.teacherName)
                                            && a.date.equals(newDate)
                                            && a.time.equals(newTime)){
                                        exists = true;
                                        break;
                                    }
                                }

                                if(exists){
                                    System.out.println("[X] This is your original appointment slot. You can’t choose the same day and time. Please pick another one.");
                                    continue;
                                }

                                while(true){
                                    System.out.print("[?] Confirm update? (Y/N): ");
                                    String c = sc.nextLine().trim().toLowerCase();

                                    if(c.equals("y")){
                                        selected.date = newDate;
                                        selected.time = newTime;
                                        selected.status = "Pending";
                                        selected.room = "N/A";
                                        System.out.println("Updated successfully!");
                                        continue actionLoop;
                                    } else if(c.equals("n")){
                                        System.out.println("[X] Update cancelled.");
                                        break;
                                    }
                                }
                                break;
                            }
                            break;
                        }
                        break;
                    }
                }
            }
        }
    }
    }
}