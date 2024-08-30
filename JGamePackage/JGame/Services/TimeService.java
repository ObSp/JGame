package JGamePackage.JGame.Services;

import JGamePackage.JGame.JGame;
import JGamePackage.lib.Signal.Connection;

public class TimeService extends Service {

    public TimeService(JGame parent) {
        super(parent);
    }

    public void waitTicks(int ticksToWait){
        for (int t = 0; t < ticksToWait; t++)
            Parent.waitForTick();
    }

    public void waitSeconds(double seconds){
        double start = Parent.GetElapsedSeconds();
        while (true){
            Parent.waitForTick();
            double curTime = Parent.GetElapsedSeconds();
            if (curTime-start > seconds) break;
        }
    }

    public void delayTicks(int ticksToWait, Runnable executor){
        new DelayObjTicks(ticksToWait, executor).start();
    }

    public void delaySeconds(double secondsToWait, Runnable executor){
        new DelayObjSeconds(secondsToWait, executor).start();
    }
    

    private class DelayObjTicks{

        int ticks;
        Runnable ex;
        int elapsed = 0;

        @SuppressWarnings("rawtypes")
        Connection con;

        public DelayObjTicks(int ticksToWait, Runnable ex){
            this.ticks = ticksToWait;
            this.ex = ex;
        }

        public void start(){
            con = Parent.OnTick.Connect(dt->{
                elapsed += 1;
                if (elapsed > ticks){
                    con.Disconnect();
                    ex.run();
                }
            });
        }
    }

    private class DelayObjSeconds{

        double seconds;
        Runnable ex;
        double elapsed = 0;

        @SuppressWarnings("rawtypes")
        Connection con;

        public DelayObjSeconds(double seconds, Runnable ex){
            this.seconds = seconds;
            this.ex = ex;
        }

        public void start(){
            con = Parent.OnTick.Connect(dt->{
                elapsed += dt;
                if (elapsed > seconds){
                    con.Disconnect();
                    ex.run();
                }
            });
        }
    }
}
