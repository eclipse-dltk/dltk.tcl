package org.eclipse.dltk.tcl.parser.definitions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.tcl.ast.ComplexString;
import org.eclipse.dltk.tcl.ast.StringArgument;
import org.eclipse.dltk.tcl.ast.Substitution;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclArgumentList;
import org.eclipse.dltk.tcl.ast.VariableReference;
import org.eclipse.dltk.tcl.definitions.Argument;
import org.eclipse.dltk.tcl.definitions.DefinitionsFactory;
import org.eclipse.dltk.tcl.definitions.TypedArgument;

public class ArgumentDefinition {
	private String name;
	private boolean defaulted = false;
	private boolean isDefaultEmtpy = false;
	private List<Argument> uses = new ArrayList<>();
	private TclArgument argument;

	static private DefinitionsFactory factory = DefinitionsFactory.eINSTANCE;

	static public List<ArgumentDefinition> get(TclArgument tclArgument) {
		List<ArgumentDefinition> list = new ArrayList<>();
		if (tclArgument instanceof StringArgument
				|| tclArgument instanceof ComplexString
				|| tclArgument instanceof Substitution
				|| tclArgument instanceof VariableReference) {
			list.add(new ArgumentDefinition(tclArgument));
		} else if (tclArgument instanceof TclArgumentList) {
			for (TclArgument sub : ((TclArgumentList) tclArgument)
					.getArguments())
				list.add(new ArgumentDefinition(sub));
		}
		return list;
	}

	private ArgumentDefinition(TclArgument argument) {
		this.argument = argument;
		if (argument instanceof StringArgument) {
			name = ((StringArgument) argument).getValue();
		} else if (argument instanceof ComplexString
				|| argument instanceof Substitution
				|| argument instanceof VariableReference) {
			name = "";
		} else if (argument instanceof TclArgumentList) {
			List<TclArgument> args = ((TclArgumentList) argument)
					.getArguments();
			if (args.size() == 0) {
				name = null;
			}
			TclArgument nameArg = args.get(0);
			if (args.size() > 1) {
				TclArgument def = args.get(1);
				if (def instanceof StringArgument
						&& "{}".equals(((StringArgument) def).getValue())) {
					isDefaultEmtpy = true;
				}
				defaulted = true;
			}
			if (nameArg instanceof StringArgument) {
				name = ((StringArgument) nameArg).getValue();
			} else if (nameArg instanceof TclArgumentList) {
				TclArgument sub = ((TclArgumentList) nameArg).getArguments()
						.get(0);

				if (sub instanceof StringArgument) {
					name = ((StringArgument) sub).getValue();
				} else {
					name = "";
				}
			}
		}
		if (name != null && name.startsWith("{"))
			name = null;
	}

	public Argument createArgument() {
		if (uses.size() > 0) {
			Argument arg = DefinitionUtils.copyArgument(uses.get(0));
			if (arg instanceof TypedArgument) {
				((TypedArgument) arg).setName(name);
			}
			return arg;
		}
		TypedArgument arg = factory.createTypedArgument();
		arg.setName(name);
		return arg;
	}

	public String getName() {
		return name;
	}

	public boolean isDefaulted() {
		return defaulted;
	}

	public TclArgument getArgument() {
		return argument;
	}

	public List<Argument> getUses() {
		return uses;
	}

	public boolean isDefaultEmtpy() {
		return isDefaultEmtpy;
	}
}
