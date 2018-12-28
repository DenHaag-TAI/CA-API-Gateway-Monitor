package nl.denhaag.rest.monitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.tree.iter.SingletonIterator;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;


public class Base64Extension extends ExtensionFunctionDefinition {
		private static final Logger logger = LogManager.getLogger();		
		private static final long serialVersionUID = 654874379518388994L;
		private static final StructuredQName funcname = new StructuredQName("tw", "http://www.denhaag.nl/xslt/extensions/tw","base64Extension");
		private Base64ExtensionCall base64ExtensionCall;

		public Base64Extension() {
			logger.debug("Base64Extension:start");
			this.base64ExtensionCall = new Base64ExtensionCall();
			logger.debug("Base64Extension:end");
		}

		@Override
		public StructuredQName getFunctionQName() {
			logger.debug("getFunctionQName:start");
			return funcname;
		}

		@Override
		public int getMinimumNumberOfArguments() {
			logger.debug("getMinimumNumberOfArguments:start");
			return 1;
		}

		public int getMaximumNumberOfArguments() {
			logger.debug("getMaximumNumberOfArguments:start");
			return 1;
		}

		@Override
		public SequenceType[] getArgumentTypes() {
			logger.debug("getArgumentTypes:start");
			return new SequenceType[] { SequenceType.OPTIONAL_STRING };
		}

		@Override
		public SequenceType getResultType(SequenceType[] sequenceTypes) {
			logger.debug("getResultType:start");
			return SequenceType.OPTIONAL_STRING;
		}

		@Override
		public ExtensionFunctionCall makeCallExpression() {
			logger.debug("makeCallExpression:start");
			return base64ExtensionCall;
		}

		class Base64ExtensionCall extends ExtensionFunctionCall {
			private static final long serialVersionUID = 6761914863093344493L;


			public SequenceIterator call(SequenceIterator[] arguments, XPathContext context) throws XPathException {
				logger.debug("call:start");
				if (arguments.length == 1) {
					String value = arguments[0].next().getStringValue();
					value = new String(Base64.decode(value));		
					value = value.replaceAll("[\n\r\t]", ", ");
					logger.debug("call:end");
					return SingletonIterator.makeIterator(new StringValue(value));
				} else {
					logger.debug("call:end");
					return SingletonIterator.makeIterator(new StringValue("ERROR"));
				}
			}


			@Override
			public Sequence call(XPathContext arg0, Sequence[] arg1) throws XPathException {
				// TODO Auto-generated method stub
				return null;
			}
		}
}
