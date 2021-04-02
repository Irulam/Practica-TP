package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.StateComparator;
import simulator.control.Controller;
import simulator.control.EpsilonEqualState;
import simulator.factories.BasicBodyBuilder;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.EpsilonEqualStatesBuilder;
import simulator.factories.Factory;
import simulator.factories.MassEqualStateBuilder;
import simulator.factories.MassLossingBodyBuilder;
import simulator.factories.MovingTowardsFixedPointBuilder;
import simulator.factories.NewtonUniversalGravitationBuilder;
import simulator.factories.NoForceBuilder;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

//TODO: no sé como funciona el proceso de recibir los estados esperados, posiblemente hay fallos en el batch mode por este motivo
public class Main {

	// default values for some parameters
	//
	private final static Double _dtimeDefaultValue = 2500.0;
	private final static String _forceLawsDefaultValue = "nlug";
	private final static String _stateComparatorDefaultValue = "epseq";
	private final static Integer _sDefaultValue = 150;
	// some attributes to stores values corresponding to command-line parameters
	//
	private static Double _dtime = null;
	private static Double _eps = null;
	private static ForceLaws _laws = null;
	private static Integer _steps = null;
	private static String _inFile = null;
	private static String _outFile = null;
	private static String _eoutFile = null;
	private static InputStream _expOutFile = null;
	private static JSONObject _forceLawsInfo = null;
	private static JSONObject _stateComparatorInfo = null;

	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<ForceLaws> _forceLawsFactory;
	private static Factory<StateComparator> _stateComparatorFactory;

	//inicializa las factorías de los cuerpos, leyes y comparadores
	private static void init() {
		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
		bodyBuilders.add(new BasicBodyBuilder());
		bodyBuilders.add(new MassLossingBodyBuilder());	
		_bodyFactory = new BuilderBasedFactory<>(bodyBuilders);

		ArrayList<Builder<ForceLaws>> gravityLawsBuilders = new ArrayList<>();
		gravityLawsBuilders.add(new NoForceBuilder());
		gravityLawsBuilders.add(new MovingTowardsFixedPointBuilder());
		gravityLawsBuilders.add(new NewtonUniversalGravitationBuilder());	
		_forceLawsFactory = new BuilderBasedFactory<>(gravityLawsBuilders);

		ArrayList<Builder<StateComparator>> stateComparatorBuilders = new ArrayList<>();
		stateComparatorBuilders.add(new MassEqualStateBuilder());
		stateComparatorBuilders.add(new EpsilonEqualStatesBuilder());
		_stateComparatorFactory = new BuilderBasedFactory<>(stateComparatorBuilders);
	}

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);

			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseStepOption(line);
			parseOutOption(line);
			parseDeltaTimeOption(line);
			parseForceLawsOption(line);
			parseStateComparatorOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e){
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());
		//output file
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where output is written. Default value: the standart output").build());
		
		//expected output
		cmdLineOptions.addOption(Option.builder("eo").longOpt("expected-output").desc("The expected utput file. If not provided no comparision is applied").build());
		
		//steps
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg().desc("An integer representing the number of simulation steps. Default value: 150 ").build());
		
		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: "
						+ _dtimeDefaultValue + ".")
				.build());

		// force laws
		cmdLineOptions.addOption(Option.builder("fl").longOpt("force-laws").hasArg()
				.desc("Force laws to be used in the simulator. Possible values: "
						+ factoryPossibleValues(_forceLawsFactory) + ". Default value: '" + _forceLawsDefaultValue
						+ "'.")
				.build());

		// gravity laws
		cmdLineOptions.addOption(Option.builder("cmp").longOpt("comparator").hasArg()
				.desc("State comparator to be used when comparing states. Possible values: "
						+ factoryPossibleValues(_stateComparatorFactory) + ". Default value: '"
						+ _stateComparatorDefaultValue + "'.")
				.build());

		return cmdLineOptions;
	}

	public static String factoryPossibleValues(Factory<?> factory) {
		if (factory == null)
			return "No values found (the factory is null)";

		String s = "";

		for (JSONObject fe : factory.getInfo()) {
			if (s.length() > 0) {
				s = s + ", ";
			}
			s = s + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
		}

		s = s + ". You can provide the 'data' json attaching :{...} to the tag, but without spaces.";
		return s;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null) {
			throw new ParseException("In batch mode an input file of bodies is required");
		}
	}

	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			if (_dtime > 0);{
				_dtime = Double.parseDouble(dt);
			}
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}


	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_eoutFile = line.getOptionValue("eo");
		try {
			if (_outFile == null) {
				_expOutFile = new FileInputStream(_eoutFile);
			}
		} catch (Exception e) {
			throw new ParseException("Invalid expected output");
		}
		
	}

	private static void parseStepOption(CommandLine line) throws ParseException {
		String s = line.getOptionValue("s", _sDefaultValue.toString());
		try {
			if(_steps>0) {
				_steps = Integer.parseInt(s);
			}
		} catch (Exception e) {
			throw new ParseException("Invalid step value: " + s);
		}

		
	}

	private static void parseOutOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("eo");
		if (_expOutFile == null) {
			throw new ParseException("In batch mode an output file of bodies is required");
		}
		
	}
	
	private static JSONObject parseWRTFactory(String v, Factory<?> factory) {

		// the value of v is either a tag for the type, or a tag:data where data is a
		// JSON structure corresponding to the data of that type. We split this
		// information
		// into variables 'type' and 'data'
		//
		int i = v.indexOf(":");
		String type = null;
		String data = null;
		if (i != -1) {
			type = v.substring(0, i);
			data = v.substring(i + 1);
		} else {
			type = v;
			data = "{}";
		}

		// look if the type is supported by the factory
		boolean found = false;
		for (JSONObject fe : factory.getInfo()) {
			if (type.equals(fe.getString("type"))) {
				found = true;
				break;
			}
		}

		// build a corresponding JSON for that data, if found
		JSONObject jo = null;
		if (found) {
			jo = new JSONObject();
			jo.put("type", type);
			jo.put("data", new JSONObject(data));
		}
		return jo;

	}

	private static void parseForceLawsOption(CommandLine line) throws ParseException {
		String fl = line.getOptionValue("fl", _forceLawsDefaultValue);
		_forceLawsInfo = parseWRTFactory(fl, _forceLawsFactory);
		if (_forceLawsInfo == null) {
			throw new ParseException("Invalid force laws: " + fl);
		}
	}

	private static void parseStateComparatorOption(CommandLine line) throws ParseException {
		String scmp = line.getOptionValue("cmp", _stateComparatorDefaultValue);
		_stateComparatorInfo = parseWRTFactory(scmp, _stateComparatorFactory);
		if (_stateComparatorInfo == null) {
			throw new ParseException("Invalid state comparator: " + scmp);
		}
	}

	private static void startBatchMode() throws Exception {
		PhysicsSimulator simulator = createsSimulator();
		StateComparator comparator = createsComparator();
		Controller controller = createsController(simulator);
		controller.loadBodies(new FileInputStream(_inFile));
		
		if (_outFile == null) { 
			controller.run(_steps, System.out, _expOutFile, comparator);
		} else {
			try (OutputStream outFile = new FileOutputStream(_outFile)) {
				controller.run(_steps, outFile, _expOutFile, comparator);
			} catch (IOException e) {
				throw new ParseException("Fail" + _outFile);
			}
		}

	}


	private static StateComparator createsComparator() {
		return new EpsilonEqualState(_eps);
	}

	private static PhysicsSimulator createsSimulator() {
		return new PhysicsSimulator(_dtime, _laws);
	}

	private static Controller createsController(PhysicsSimulator simulator) {
		Controller controller = new Controller(simulator, _bodyFactory, _forceLawsFactory);
		controller.setForceLaws(_forceLawsInfo);
		
		return controller;

	}

	private static void start(String[] args) throws Exception {
		parseArgs(args);
		startBatchMode();
	}

	public static void main(String[] args) {
		try {
			init();
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
