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

public class Chapter_2_Gossip_Circle {
	
	// In this example we will create several nodes
	// and agents will communicate also to remote nodes.
	// For this purpose we create a virtual port to which all nodes will be connected
	public static VirtualPort vp = new VirtualPort(8080);

	public static void main(String[] argv) {

		// Number of friends in the circle of gossip
		int N = 10;

		// Array of nodes (one for each friend)
		Node[] node = new Node[N];
		// Array of gossiper friends
		Gossiper[] gossiper = new Gossiper[N];


		for(int i=0; i<N; i++){
			node[i]= new Node("node"+i, new TupleSpace());
			// We need to add the common virtual port to the node 
			node[i].addPort(vp);
			// We create the gossiper agent with the name of the next friend in the circle
			// as second parameter of the constructor
			gossiper[i] = new Gossiper("node"+i,"node"+((i+1)%N));
			node[i].addAgent(gossiper[i]);
		}		
		
		// It is important in this example to start the nodes *after* having created the nodes
		// Otherwise an agent may try to access a non-existing node, thus raising an exception
		for(int i=0; i<N; i++){
			node[i].start();
		}		

	}

	public static class Gossiper extends Agent {

		// A gossiper agent needs to record the name of this node
		String home;
		// ...and the name/target of the node of the next friend in the circle
		// we use a target of type PointToPoint which is necessary for remote operations
		PointToPoint next;

		public Gossiper(String home, String next) {
			super("broadcaster");
			this.home = home;
			// We create the PointToPoint target to access the remote tuple space
			this.next = new PointToPoint(next, vp.getAddress());
		}

		@Override
		protected void doRun() {
			// The templates for gossips
			Template new_gossip = new Template(
					new ActualTemplateField("new gossip"),
					new FormalTemplateField(String.class),
					new FormalTemplateField(String[].class)
					);
			// And the tuple to save it
			Tuple t;
			String gossip_message;
			String[] gossip_set;
			try {
				// Only the first gossiper knows a gossip
				if (home.equals("node0")) {
					gossip_message = "Alice loves Bob";
					System.out.println("Gossiper " + home + " generating gossip \"" + gossip_message + "\"...");
					put(new Tuple("new gossip",gossip_message,new String[]{"node2","node3"}),Self.SELF);
					//gossip_message = "Bob loves Alice";
					//System.out.println("Gossiper " + home + " generating gossip \"" + gossip_message + "\"...");
					//put(new Tuple("new gossip",gossip_message),Self.SELF);
				}
				while(true){
					t = get(new_gossip,Self.SELF);
					// Check if the gossip is old
					gossip_message = (t.getElementAt(String.class,1));
					gossip_set = (t.getElementAt(String[].class,2));
					// Note how we test the result of a query
					// Null means failure
					if (queryp(new Template(new ActualTemplateField("old gossip"),new ActualTemplateField(gossip_message),new FormalTemplateField(String[].class))) == null){
						// See how the name of a node can be accessed from the PointToPoint target
						System.out.println("Gossiper " + home + " forwarding \"" + gossip_message + "\" to gossiper " + next.getName());
						// Forward the gossip
						put(t,next);
						boolean set = false;
						for(String s : t.getElementAt(String[].class, 2)){
							if(s.equals(home)){
								set = true;
							}
						}
						if(set){
							//print set
							System.out.println(home + " stored: \"" + gossip_message + "\"");
							// Save the gossip
							put(new Tuple("old gossip",gossip_message,gossip_set),Self.SELF);
						}						
					}	
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}