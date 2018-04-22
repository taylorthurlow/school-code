public class ShortestJobFirstScheduler extends Scheduler
{
    public ShortestJobFirstScheduler(Processor theCPU)
    {
        super(theCPU);
    }

    public String algName() { return "Shortest Job First Scheduler"; }

    // This function does the scheduling work!
    public void tick()
    {
        // tip: you can call job.timeLeft();
    }

    public void testCase1(Processor cpu)
    {
        cpu.addJob(1, 1, 50, "10,20,30,40", 1, 126);
        cpu.addJob(2, 1, 30, "7,15,22", 1, 101);
        cpu.addJob(3, 1, 20, "6,14", 1, 35);
        cpu.addJob(4, 1, 20, "6,14", 1, 41);
    }

    public void testCase2(Processor cpu)
    {
        cpu.addJob(1, 0, 30, "9,18,27", 4, 76);
        cpu.addJob(2, 1, 20, "3,6,9,12,15", 5, 81);
        cpu.addJob(3, 2, 30, "5,10,15,20,25", 2, 136);
        cpu.addJob(4, 21, 21, "20", 4, 65);
        cpu.addJob(5, 42, 29, "28", 3, 217);
        cpu.addJob(6, 71, 18, "5,10,15", 3, 127);
        cpu.addJob(7, 89, 13, "4,8,12", 4, 119);
        cpu.addJob(8, 102, 19, "4,8,12,16", 2, 175);
        cpu.addJob(9, 121, 19, "9,18", 1, 182);
        cpu.addJob(10, 140, 26, "6,12,18,24", 1, 234);
    }
}
