package somfo.problems.SOP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import somfo.core.Problem;
import somfo.util.MATRIX;

public abstract class Function extends Problem {

	double[][] rotationMatrix_;

	double[] shiftMatrix_;

	public void rotationFileReading(String name) {
		rotationMatrix_ = new double[numberOfVariables_][numberOfVariables_];
		try (BufferedReader br = new BufferedReader(new FileReader(name))) {
			String line;
			String[] S;
			int counter = 0;

			while ((line = br.readLine()) != null) {
				S = line.split("	");
				for (int i = 0; i < S.length; i++) {
					rotationMatrix_[counter][i] = Double.parseDouble(S[i]);
				}
				counter++;
			}
			line = null;
			if (counter != numberOfVariables_) {
				IOException e = new IOException();
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ShiftFileReading(String name) {
		shiftMatrix_ = new double[numberOfVariables_];
		try (BufferedReader br = new BufferedReader(new FileReader(name))) {
			String line;
			String[] S;
			int counter = 0;

			while ((line = br.readLine()) != null) {
				shiftMatrix_[counter] = Double.parseDouble(line);
				counter++;
			}
			line = null;
			if (counter != numberOfVariables_) {
				IOException e = new IOException();
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double[] decode(double[] x) {
		double[] ret = new double[numberOfVariables_];

		for (int i = 0; i < numberOfVariables_; i++) {
			ret[i] = x[i] * (upperLimit_[i] - lowerLimit_[i]) + lowerLimit_[i];
		}

		for (int i = 0; i < numberOfVariables_; i++) {
			ret[i] = ret[i] - shiftMatrix_[i];
		}

		ret = MATRIX.calc(rotationMatrix_, ret);

		return ret;
	}

}
