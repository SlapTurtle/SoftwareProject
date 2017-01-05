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
import org.cmg.resp.topology.VirtualPortAddress;

public class opg22 {
	
	// In this example we will create several nodes
	// and agents will communicate also to remote nodes.
	// For this purpose we create a virtual port to which all nodes will be connected
	public static VirtualPort vp = new VirtualPort(8080);

	public static void main(String[] argv) {
		Node PQRS = new Node("PQGRS", new TupleSpace());
		
		Agent P = new AgentP("p", "q", "r");
		Agent Q = new AgentQR("q", "s");
		Agent R = new AgentQR("r", "s");
		Agent S = new AgentS("s", "r", "q");
		
		PQRS.addAgent(P);
		PQRS.addAgent(Q);
		PQRS.addAgent(R);
		PQRS.addAgent(S);
		
		PQRS.start();
	}
	
	public static class AgentP extends Agent{
		
		String next1;
		String next2;
		public AgentP(String name, String n1, String n2) {
			super(name);
			next1 = n1;
			next2 = n2;
		}

		@Override
		protected void doRun() throws Exception {
			Tuple t1 = new Tuple(next1, 2);
			Tuple t2 = new Tuple(next2, 3);
			put(t1, Self.SELF);
			put(t2, Self.SELF);
			System.out.println(name+" put "+2+" for "+next1);
			System.out.println(name+" put "+3+" for "+next2);
		}	
	}
	
	public static class AgentQR extends Agent{
		
		String next;
		
		public AgentQR(String name, String next) {
			super(name);
			this.next = next;
		}

		@Override
		protected void doRun() throws Exception {
			Template what = new Template(
					new ActualTemplateField(name),
					new FormalTemplateField(Integer.class)
					);
			
			Tuple tOld, tNew;
			while(true){
				if(query(what, Self.SELF) != null){
					tOld = get(what, Self.SELF);
					int i = tOld.getElementAt(Integer.class, 1);
					System.out.println(name+" got "+i+" for P");
					i *= i;
					tNew = new Tuple(name,next,i);
					if(next != null){
						System.out.println(name+" put "+i+" for "+next);
						put(tNew, Self.SELF);
					}					
				}
			}
		}	
	}
	
	public static class AgentS extends Agent{
		
		String prev1;
		String prev2;
		public AgentS(String name, String prev1, String prev2) {
			super(name);
			this.prev1 = prev1;
			this.prev2 = prev2;
		}

		@Override
		protected void doRun() throws Exception {
			Template what1 = new Template(
					new ActualTemplateField(prev1),
					new ActualTemplateField(name),
					new FormalTemplateField(Integer.class)
					);
			
			Template what2 = new Template(
					new ActualTemplateField(prev2),
					new ActualTemplateField(name),
					new FormalTemplateField(Integer.class)
					);
			
			Tuple t1,t2;
			while(true){
				if(query(what1, Self.SELF) != null && query(what2, Self.SELF) != null){
					t1 = get(what1, Self.SELF);
					t2 = get(what2, Self.SELF);
					int i = t1.getElementAt(Integer.class, 2);
					int j = t2.getElementAt(Integer.class, 2);
					System.out.println("from "+prev1+":"+i+" ; from "+prev2+":"+j+" ; "+i+" + "+j+" = "+(j+i));
				}
			}
		}	
	}
}