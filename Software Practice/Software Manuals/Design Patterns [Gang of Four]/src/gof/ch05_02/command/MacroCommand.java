package gof.ch05_02.command;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 241-242.
 * Sample code to illustrate the {@linkplain gof.designpatterns.Command Command}
 * design pattern. This class merely manages a list of Commands. The manual
 * notes (p. 241) that to implement unexecute(), the logic must traverse the
 * list in reverse order.</div>
 *
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class MacroCommand extends Command implements gof.designpatterns.Command {

	List<Command> commands;

	public MacroCommand() {
		this.commands = new ArrayList<Command>();
	}

	public void add(Command command) {
		this.commands.add(command);
	}

	public void remove(Command command) {
		this.commands.remove(command);
	}

	@Override
	public void execute() {
		ListIterator<Command> commandIterator = this.commands.listIterator();
		while (commandIterator.hasNext()) {
			Command command = commandIterator.next();
			command.execute();
		}
	}

	public void finalize() {
		// Clean-up before deallocation.
	}
}
