public class RoundRobinScheduler extends Scheduler
{
    public RoundRobinScheduler(Processor theCPU)
    {
        super(theCPU);
    }

    public String algName() { return "Round Robin Scheduler"; }

    // This function does the scheduling work!
    public void tick()
    {
        // TBD
    }

    public void testCase1(Processor cpu)
    {
        cpu.addJob(1, 1, 50, "10,20,30,40", 1, 126);
        cpu.addJob(2, 1, 30, "7,15,22", 1, 103);
        cpu.addJob(3, 1, 20, "6,14", 1, 77);
        cpu.addJob(4, 1, 20, "6,14", 1, 83);
    }

    public void testCase2(Processor cpu)
    {
        cpu.addJob(1, 0, 30, "9,18,27", 4, 140);
        cpu.addJob(2, 1, 20, "3,6,9,12,15", 5, 104);
        cpu.addJob(3, 2, 30, "5,10,15,20,25", 2, 161);
        cpu.addJob(4, 21, 21, "20", 4, 116);
        cpu.addJob(5, 42, 29, "28", 3, 200);
        cpu.addJob(6, 71, 18, "5,10,15", 3, 191);
        cpu.addJob(7, 89, 13, "4,8,12", 4, 180);
        cpu.addJob(8, 102, 19, "4,8,12,16", 2, 211);
        cpu.addJob(9, 121, 19, "9,18", 1, 213);
        cpu.addJob(10, 140, 26, "6,12,18,24", 1, 235);
    }
}
