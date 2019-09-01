import java.io.File

private val IGNORED_DIRS = listOf(".git", ".idea", "exercism-jenkins-runner", "teams", "users")
private val templateName = "README.template.md"
private val readmeName = "README.md"

private var template = File(templateName).readText()

File(".").listFiles()
        ?.filter { it.isDirectory && !IGNORED_DIRS.contains(it.name) }
        ?.forEach {
            val exercisesCountPlaceholder = "{${it.name}-count}"
            val exercisesCount = countSolutions(it).toString()
            template = template.replace(exercisesCountPlaceholder, exercisesCount)
        }

File(readmeName).writeText(template)

fun countSolutions(dir: File): Int {
    return dir.listFiles()
            ?.filter { it.isDirectory }
            ?.filter { it.containsExercism() || it.containsSolutionJson() }
            ?.size ?: 0
}

fun File?.containsExercism() = this?.listFiles()?.any { it.isDirectory && it.name == ".exercism" } ?: false

fun File?.containsSolutionJson() = this?.listFiles()?.any { it.name == ".solution.json" } ?: false
