package pack002;

import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.comp.Node;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.cmg.resp.topology.PointToPoint;
import org.cmg.resp.topology.Self;
import org.cmg.resp.topology.VirtualPort;

public class opg23 {
	
	public static VirtualPort vp = new VirtualPort(8080);

	public static void main(String[] argv) {

		int N = 10;
		Node[] node = new Node[N];
		IndentityFinder[] IndentityFinder = new IndentityFinder[N];


		for(int i=0; i<N; i++){
			node[i]= new Node("node"+i, new TupleSpace());
			node[i].addPort(vp);
			IndentityFinder[i] = new IndentityFinder("node"+i,"node"+((i+1)%N),(int)(Math.random()*10));
			node[i].addAgent(IndentityFinder[i]);
		}		
		
		for(int i=0; i<N; i++){
			node[i].start();
		}
		try {
			Thread.sleep(100);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static class IndentityFinder extends Agent {

		String home;
		PointToPoint next;
		int leaderId;

		public IndentityFinder(String home, String next, int id) {
			super("Identifier");
			this.home = home;
			this.next = new PointToPoint(next, vp.getAddress());
			this.leaderId = id;
		}

		@Override
		protected void doRun() {
			Template what = new Template(
					new ActualTemplateField("Identifier"),
					new FormalTemplateField(Integer.class)
					);
			try {
				System.out.println("Node " + home + " generating id \"" + this.leaderId + "\" in "+next+"...");
				put(new Tuple("Identifier",this.leaderId),next);
				
				Tuple t;
				int id;
				while(true){
					t = get(what,Self.SELF);
					id = (t.getElementAt(Integer.class,1));
					if(id > this.leaderId){
						System.out.println(home+" forwarding id "+id+" to "+next+" ; current leader: "+leaderId+"/"+id);
						leaderId = id;
						put(t,next);
					}
					else{
						System.out.println(home+" discarding id "+id+" ; current leader: "+leaderId);	
					}
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}