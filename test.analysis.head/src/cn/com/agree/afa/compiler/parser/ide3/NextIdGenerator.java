package cn.com.agree.afa.compiler.parser.ide3;

import cn.com.agree.afa.compiler.model.NodeModel;
import java.util.Set;
import java.util.TreeSet;

public class NextIdGenerator {
	private TreeSet<Integer> ids = new TreeSet<>();

	public NextIdGenerator(Set<NodeModel> nodes) {
		for (NodeModel node : nodes)
			this.ids.add(Integer.valueOf(node.getId()));
	}

	public int nextId() {
		int last = ((Integer) this.ids.last()).intValue();
		do
			last++;
		while (this.ids.contains(Integer.valueOf(last)));

		this.ids.add(Integer.valueOf(last));
		return last;
	}
}
