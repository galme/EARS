package org.um.feri.ears.util;

import java.util.Comparator;

import org.um.feri.ears.problems.moo.MOSolutionBase;
import org.um.feri.ears.problems.moo.ParetoSolution;

public class SolutionUtils {

	/**
	 * Return the best solution between those passed as arguments. If they are equal or incomparable
	 * one of them is chosen randomly.
	 * @param solution1
	 * @param solution2
	 * @return The best solution
	 */
	public static <T> MOSolutionBase<T> getBestSolution(MOSolutionBase<T> solution1, MOSolutionBase<T> solution2, Comparator comparator) {
		MOSolutionBase<T> result ;
		int flag = comparator.compare(solution1, solution2);
		if (flag == -1) {
			result = solution1;
		} else if (flag == 1) {
			result = solution2;
		} else {
			if (Util.nextDouble() < 0.5) {
				result = solution1;
			} else {
				result = solution2;
			}
		}

		return result ;
	}


	/**
	 * Returns the euclidean distance between a pair of solutions in the objective space
	 * @param firstSolution
	 * @param secondSolution
	 * @return
	 */
	static <T> double distanceBetweenObjectives(MOSolutionBase<T> firstSolution, MOSolutionBase<T> secondSolution) {

		double diff;  
		double distance = 0.0;
		//euclidean distance
		for (int nObj = 0; nObj < firstSolution.numberOfObjectives();nObj++){
			diff = firstSolution.getObjective(nObj) - secondSolution.getObjective(nObj);
			distance += Math.pow(diff,2.0);           
		} // for   

		return Math.sqrt(distance);
	}

	/** Returns the minimum distance from a <code>Solution</code> to a
	 * <code>SolutionSet according to the encodings.variable values</code>.
	 * @param solution The <code>Solution</code>.
	 * @param solutionList The <code>List<Solution></></code>.
	 * @return The minimum distance between solution and the set.
	 */
	public static <T extends Number> double distanceToSolutionListInSolutionSpace(MOSolutionBase<T> solution,
			ParetoSolution<T> solutionList){
		//At start point the distance is the max
		double distance = Double.MAX_VALUE;

		// found the min distance respect to population
		for (int i = 0; i < solutionList.size();i++){
			double aux = distanceBetweenSolutions(solution,solutionList.get(i));
			if (aux < distance)
				distance = aux;
		} // for

		//->Return the best distance
		return distance;
	} // distanceToSolutionSetInSolutionSpace

	/** Returns the distance between two solutions in the search space.
	 *  @param solutionI The first <code>Solution</code>.
	 *  @param solutionJ The second <code>Solution</code>.
	 *  @return the distance between solutions.
	 */
	public static <T extends Number> double distanceBetweenSolutions(MOSolutionBase<T> solutionI, MOSolutionBase<T> solutionJ) {
		double distance = 0.0;

		double diff;    //Auxiliar var
		//-> Calculate the Euclidean distance
		for (int i = 0; i < solutionI.numberOfVariables() ; i++){
			diff = solutionI.getValue(i).doubleValue() - solutionJ.getValue(i).doubleValue();
			distance += Math.pow(diff,2.0);
		} // for
		//-> Return the euclidean distance
		return Math.sqrt(distance);
	} // distanceBetweenSolutions


}

