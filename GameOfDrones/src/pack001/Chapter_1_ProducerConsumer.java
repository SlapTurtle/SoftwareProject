package pack001;
import java.util.LinkedList;

import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.comp.Node;
import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.cmg.resp.topology.Self;

public class Chapter_1_ProducerConsumer {
	
	static String TAG_FOOD = "food";
	static String TAG_DRUGS = "drugs";
	static String TAG_SHOPLOCK = "lock";
	
	public static void main(String[] argv) {

		// We create here a single node that we call "fridge"
		Node Appartment = new Node("fridge", new TupleSpace());

		// We create Alice and Bob as Producer/Consumer agents
		// The constructor of agents takes the name of the agent as argument
		Agent Alice = new Producer("Alice");
		Agent Bob = new Consumer("Bob", new String[]{TAG_FOOD, TAG_DRUGS},3);
		Agent Charlie = new Consumer("Charlie", new String[]{TAG_FOOD, TAG_DRUGS},3);
		Agent Dave = new Consumer("Dave", new String[]{TAG_DRUGS});
		// We add both agents to the fridge node
		Appartment.addAgent(Alice);
		Appartment.addAgent(Bob);
		Appartment.addAgent(Charlie);
		//Appartment.addAgent(Dave);
		// We start the node
		Appartment.put(new Tuple(TAG_SHOPLOCK, TAG_FOOD));
		Appartment.put(new Tuple(TAG_SHOPLOCK, TAG_DRUGS));
		Appartment.start();

	}

	public static class Producer extends Agent {

		// This constructor records the name of the agent
		public Producer(String name) {
			super(name);
		}

		// This is the function invoked when the agent starts running in a node
		@Override
		protected void doRun() {
			try {
				System.out.println(name + " adding items to the grocery list...");
				System.out.println(name + " adding milk(1)");
				// put takes to arguments: the tuple to be put and the target node
				// tuples are objects of class Tuple, whose constructor takes a list of values
				// Self.SELF is the node where the agent resides
				put(new Tuple("milk",1,TAG_FOOD), Self.SELF);
				System.out.println(name + " adding soap(2)");
				put(new Tuple("soap",2,TAG_DRUGS), Self.SELF);
				System.out.println(name + " adding butter(3)");
				put(new Tuple("butter",3,TAG_FOOD), Self.SELF);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static class Consumer extends Agent {
		
		private String[] itemType;
		private int minItemCount;
		
		public Consumer(String name, String[] itemType, int minItemCount) {
			super(name);
			Init(itemType, minItemCount);
		}
		
		public Consumer(String name, String[] itemType){
			super(name);
			Init(itemType, 1);
		}
		
		private void Init(String[] itemType, int minItemCount){
			this.itemType = itemType;
			this.minItemCount = minItemCount;
		}

		@Override
		protected void doRun() {
			// Note how templates are created in jRESP
			// In place of binding variables we need to use so-called formal fields
			// We will see later how these fields can be saved into variables
			// The formal field constructor needs a class as parameter
			Template[] what = new Template[itemType.length];
			for(int i = 0; i < itemType.length; i++){
				what[i] = new Template(
						new FormalTemplateField(String.class),
						new FormalTemplateField(Integer.class),
						new ActualTemplateField(itemType[i])
						);
			}
			//makes templates for keys
			Template tp[] = new Template[itemType.length];
			for(int i = 0; i < itemType.length; i++){
				tp[i] = new Template(new ActualTemplateField(TAG_SHOPLOCK), new ActualTemplateField(itemType[i]));
			}
			// The tuple is necessary to capture the result of a get operation
			try {
				while (true) {					
					Tuple tu[] = new Tuple[itemType.length];
					for(int i = 0; i < itemType.length; i++){
						if(tp[i] != null){
							tu[i] = getp(tp[i]);
						}
					}
					int[] counts = new int[itemType.length];
					int count = 0;
					for(int i = 0; i < itemType.length; i++){
						if(tu[i] != null){
							counts[i] = queryAll(what[i]).size();
							if(counts[i] > 0){
								count += counts[i];
							}
						}
					}							
					if(count >= minItemCount){
						System.out.println(name + " is shopping");						
						for(int i = 0; i < itemType.length; i++){
							if(counts[i] > 0){
								LinkedList<Tuple> list = getAll(what[i]);
								if(list != null){
									for(Tuple t : list){
										System.out.println(name + " shopping of type " + t.getElementAt(String.class, 2) + ", "+ t.getElementAt(Integer.class,1) + " units of " + t.getElementAt(String.class, 0) + "...");
									}
								}
							}
						}						
					}
					for(Tuple tut : tu){
						if(tut != null){
							put(tut, Self.SELF);
						}					
					}
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}