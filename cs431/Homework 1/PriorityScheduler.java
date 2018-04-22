public class PriorityScheduler extends Scheduler
{
    public PriorityScheduler(Processor theCPU)
    {
        super(theCPU);
    }

    public String algName() { return "Priority Scheduler"; }

    // This function does the scheduling work!
    public void tick()
    {
        // You can call job.priority() to get the priority of the job

        // TBD
    }

    public void testCase1(Processor cpu)
    {
        cpu.addJob(1, 1, 50, "10,20,30,40", 1, 141);
        cpu.addJob(2, 1, 30, "7,15,22", 3, 51);
        cpu.addJob(3, 1, 20, "6,14", 2, 81);
        cpu.addJob(4, 1, 20, "6,14", 3, 43);
    }

    public void testCase2(Processor cpu)
    {
        cpu.addJob(1, 0, 30, "9,18,27", 4, 77);
        cpu.addJob(2, 1, 20, "3,6,9,12,15", 5, 46);
        cpu.addJob(3, 2, 30, "5,10,15,20,25", 2, 189);
        cpu.addJob(4, 21, 21, "20", 4, 70);
        cpu.addJob(5, 42, 29, "28", 3, 131);
        cpu.addJob(6, 71, 18, "5,10,15", 3, 137);
        cpu.addJob(7, 89, 13, "4,8,12", 4, 117);
        cpu.addJob(8, 102, 19, "4,8,12,16", 2, 182);
        cpu.addJob(9, 121, 19, "9,18", 1, 220);
        cpu.addJob(10, 140, 26, "6,12,18,24", 1, 237);
    }
}
