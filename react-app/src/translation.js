const errorsMap = {
    "User with such an email already exists": "пользователь с такой почтой уже существует",
    "This email is not registered": "пользователь с такой почтой не зарегистрирован",
    "Wrong password": "неверный пароль",
    "User email is incorrect": "некорректная почта",
    "User not found": "пользователь не найден",
    "Serializing problem": "ошибка при сериализации данных",
    "Password is incorrect": "неверный пароль",
    "Participant not found": "участник не найден",
    "Only registered users can create polls": "только зарегистрированные пользователи могут создавать опросы",
    "Poll is empty": "опрос пуст",
    "Email is incorrect": "некорректная почта",
    "Question is incorrect": "некорректный вопрос",
    "Need more answers variants": "добавьте больше вариантов ответа",
    "Incorrect answer variant": "неверный вариант ответа",
    "Need more participants": "должен быть хотя бы один участник",
    "Incorrect participant email": "некорректная почта участника",
    "Password has been used": "ваш одноразовый пароль для прохождения этого опроса уже был использован",
    "Answer is incorrect": "некорректный ответ",
    "Answers must be unique": "не может быть одинаковых вариантов ответа",
    "Participants' emails must be unique": "у участников не могут совпадать почты"
}

export default function translateToRussian(errorMessage) {
    if (errorMessage in errorsMap) {
        return errorsMap[errorMessage];
    }
    return "что-то пошло не так";
}